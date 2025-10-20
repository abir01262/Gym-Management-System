package com.gymapp;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;

public final class SocketBus {
    // Single-machine pub/sub on localhost
    private static final int PORT = 50555;
    private static volatile boolean started = false;
    private static volatile boolean isServer = false;

    private static ServerSocket serverSocket;
    private static final List<Socket> clients = new CopyOnWriteArrayList<>();
    private static Socket clientSocket;

    private static final List<BiConsumer<String,String>> listeners = new CopyOnWriteArrayList<>();
    private static final Object sendLock = new Object();

    private SocketBus() {}

    public static void start() {
        if (started) return;
        started = true;

        try {
            // Try to be the server (first instance)
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress("127.0.0.1", PORT));
            isServer = true;
            runServer();
            // server also listens to its own broadcasts via local listeners
        } catch (IOException bindFailed) {
            // Another instance is server: become client
            isServer = false;
            connectClient();
        }
    }

    public static void stop() {
        started = false;
        if (isServer) {
            try { for (Socket s : clients) try { s.close(); } catch (Exception ignored) {} } catch (Exception ignored) {}
            try { if (serverSocket != null) serverSocket.close(); } catch (Exception ignored) {}
        } else {
            try { if (clientSocket != null) clientSocket.close(); } catch (Exception ignored) {}
        }
    }

    public static void addListener(BiConsumer<String,String> listener) {
        listeners.add(listener);
    }

    public static void removeListener(BiConsumer<String,String> listener) {
        listeners.remove(listener);
    }

    /** Publish an event to everybody (other processes + this process). */
    public static void publish(String channel, String payload) {
        String json = "{\"ch\":\"" + escape(channel) + "\",\"ts\":\"" + Instant.now() + "\",\"p\":\"" + escape(payload) + "\"}\n";
        // Deliver locally first
        deliverLocal(channel, payload);

        if (!started) return;

        if (isServer) {
            // broadcast to all clients
            broadcastToClients(json);
        } else {
            // send to server
            sendToServer(json);
        }
    }

    // ===== internals =====

    private static void runServer() {
        var pool = Executors.newCachedThreadPool(r -> {
            Thread t = new Thread(r, "SocketBus-Server");
            t.setDaemon(true);
            return t;
        });

        // Accept loop
        pool.execute(() -> {
            try {
                while (started && !serverSocket.isClosed()) {
                    Socket s = serverSocket.accept();
                    s.setTcpNoDelay(true);
                    clients.add(s);
                    startReadLoopOn(s, pool);
                }
            } catch (IOException ignored) {
            }
        });
    }

    private static void startReadLoopOn(Socket s, java.util.concurrent.Executor pool) {
        pool.execute(() -> {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    // when server receives from one client -> broadcast to others + local
                    String[] parsed = parse(line);
                    String ch = parsed[0], p = parsed[1];
                    deliverLocal(ch, p);
                    broadcastToClients(line, s); // to others (not back to sender)
                }
            } catch (IOException ignored) {
            } finally {
                try { s.close(); } catch (Exception ignored) {}
                clients.remove(s);
            }
        });
    }

    private static void broadcastToClients(String json) {
        broadcastToClients(json, null);
    }

    private static void broadcastToClients(String json, Socket except) {
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        for (Socket c : clients) {
            if (except != null && c == except) continue;
            try {
                synchronized (sendLock) {
                    OutputStream os = c.getOutputStream();
                    os.write(bytes);
                    os.flush();
                }
            } catch (IOException e) {
                try { c.close(); } catch (Exception ignored) {}
                clients.remove(c);
            }
        }
    }

    private static void connectClient() {
        var pool = Executors.newSingleThreadExecutor(r -> {
            Thread t = new Thread(r, "SocketBus-Client");
            t.setDaemon(true);
            return t;
        });

        pool.execute(() -> {
            while (started) {
                try {
                    clientSocket = new Socket();
                    clientSocket.connect(new InetSocketAddress("127.0.0.1", PORT), 1500);
                    clientSocket.setTcpNoDelay(true);

                    try (BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            String[] parsed = parse(line);
                            deliverLocal(parsed[0], parsed[1]);
                        }
                    }
                } catch (IOException ignored) {
                } finally {
                    try { if (clientSocket != null) clientSocket.close(); } catch (Exception ignored) {}
                    clientSocket = null;
                }
                // Retry connect after a short delay
                try { Thread.sleep(800); } catch (InterruptedException ignored) {}
            }
        });
    }

    private static void sendToServer(String json) {
        try {
            if (clientSocket == null || clientSocket.isClosed()) return;
            synchronized (sendLock) {
                OutputStream os = clientSocket.getOutputStream();
                os.write(json.getBytes(StandardCharsets.UTF_8));
                os.flush();
            }
        } catch (IOException ignored) {}
    }

    private static void deliverLocal(String ch, String payload) {
        for (var l : listeners) {
            try { l.accept(ch, payload); } catch (Exception ignored) {}
        }
    }

    private static String[] parse(String line) {
        // super light parser for {"ch":"...","p":"..."}
        String ch = extract(line, "\"ch\":\"", "\"");
        String p  = extract(line, "\"p\":\"",  "\"");
        return new String[]{unescape(ch), unescape(p)};
    }

    private static String extract(String src, String start, String end) {
        int i = src.indexOf(start);
        if (i < 0) return "";
        int j = src.indexOf(end, i + start.length());
        if (j < 0) return "";
        return src.substring(i + start.length(), j);
    }

    private static String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "");
    }
    private static String unescape(String s) {
        if (s == null) return "";
        return s.replace("\\n", "\n").replace("\\\"", "\"").replace("\\\\", "\\");
    }
}
