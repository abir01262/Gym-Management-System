package com.gymapp.net;

import java.io.*;
import java.net.*;
import java.util.*;

public class SocketHub {
    private static SocketHub INSTANCE;
    public static final int PORT = 5050;

    private final Map<String, Client> clients = new HashMap<>();      // sessionId -> client
    private final Map<String, String> idToSession = new HashMap<>();  // "ROLE:id" -> sessionId

    private ServerSocket server;
    private Thread acceptThread;

    public static synchronized SocketHub get() {
        if (INSTANCE == null) INSTANCE = new SocketHub();
        return INSTANCE;
    }

    public synchronized void start() {
        if (server != null) return;
        try {
            server = new ServerSocket(PORT, 50, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) { throw new RuntimeException(e); }
        acceptThread = new Thread(this::acceptLoop, "SocketHub-Acceptor");
        acceptThread.setDaemon(true);
        acceptThread.start();
    }

    private void acceptLoop() {
        try {
            while (!server.isClosed()) {
                Socket s = server.accept();
                Client c = new Client(s);
                c.start();
            }
        } catch (IOException ignored) { }
    }

    private class Client {
        final Socket socket;
        final BufferedReader in;
        final BufferedWriter out;
        String sessionId;
        Role role;
        String actorId;
        String actorName;

        Client(Socket s) {
            try {
                socket = s;
                in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            } catch (IOException e) { throw new RuntimeException(e); }
        }

        void start() { new Thread(this::readLoop, "SocketHub-Client").start(); }

        void readLoop() {
            try {
                String line;
                while ((line = in.readLine()) != null) {
                    Message m = JsonUtil.fromJson(line, Message.class);
                    handle(m, this);
                }
            } catch (Exception ignored) {
            } finally {
                if (sessionId != null) {
                    synchronized (SocketHub.this) {
                        clients.remove(sessionId);
                        idToSession.values().removeIf(v -> v.equals(sessionId));
                    }
                }
                try { socket.close(); } catch (Exception ignored) {}
            }
        }

        void send(Message m) {
            try {
                out.write(JsonUtil.toJson(m));
                out.write("\n");
                out.flush();
            } catch (IOException e) { /* ignore */ }
        }
    }

    private synchronized void handle(Message m, Client c) {
        switch (m.type) {
            case SESSION_JOIN -> {
                c.sessionId = UUID.randomUUID().toString();
                c.role = m.fromRole;
                c.actorId = m.fromId;
                c.actorName = m.fromName;
                clients.put(c.sessionId, c);
                idToSession.put(key(c.role, c.actorId), c.sessionId);
            }
            case SESSION_LEAVE -> {
                if (c.sessionId != null) {
                    clients.remove(c.sessionId);
                    idToSession.remove(key(c.role, c.actorId));
                }
            }
            case PUBLIC_CHAT -> {
                PersistenceManager.savePublic(m);
                broadcast(m);
            }
            case NOTICE -> {
                // Save once (coach-origin); deliver per-member with toId set
                PersistenceManager.saveNotice(m);
                routeNotice(m);
            }
            case PRIVATE_CHAT -> {
                PersistenceManager.savePrivate(m);
                routePrivate(m);
            }
            case TASK_ASSIGN, TASK_UPDATE -> {
                PersistenceManager.saveTask(m);
                routeTask(m);
            }
        }
    }

    private String key(Role r, String id) { return r + ":" + id; }

    private void broadcast(Message m) {
        for (Client cl : clients.values()) cl.send(m);
    }

    private void routeNotice(Message m) {
        if (m.fromRole == Role.COACH) {
            List<String> members = Router.get().membersForCoach(m.fromId);
            for (String mid : members) {
                Message copy = copyOf(m);
                copy.toId = mid;                    // IMPORTANT: set recipient
                sendTo(Role.MEMBER, mid, copy);
            }
            // Echo back to coach so they see what was sent
            Message echo = copyOf(m);
            echo.toId = m.fromId;
            sendTo(Role.COACH, m.fromId, echo);
        } else if (m.fromRole == Role.ADMIN) {
            broadcast(m);
        } else if (m.fromRole == Role.MEMBER) {
            // Normally members don't send notices; ignore or route to their coach if you want
        }
    }

    private void routePrivate(Message m) {
        if (m.fromRole == Role.COACH) {
            if (Router.get().isCoachOfMember(m.fromId, m.toId)) {
                sendTo(Role.MEMBER, m.toId, m);
            }
        } else if (m.fromRole == Role.MEMBER) {
            String coachId = Router.get().coachForMember(m.fromId);
            if (coachId != null && coachId.equals(m.toId) && Router.get().isCoachOfMember(coachId, m.fromId)) {
                sendTo(Role.COACH, coachId, m);
            }
        }
    }

    private void routeTask(Message m) {
        if (m.type == MessageType.TASK_ASSIGN && m.fromRole == Role.COACH) {
            if (m.toId != null && Router.get().isCoachOfMember(m.fromId, m.toId)) {
                sendTo(Role.MEMBER, m.toId, m);
            }
        } else if (m.type == MessageType.TASK_UPDATE && m.fromRole == Role.MEMBER) {
            String coachId = Router.get().coachForMember(m.fromId);
            if (coachId != null) sendTo(Role.COACH, coachId, m);
        }
    }

    private void sendTo(Role role, String id, Message m) {
        String sid = idToSession.get(key(role, id));
        if (sid == null) return;
        Client cl = clients.get(sid);
        if (cl != null) cl.send(m);
    }

    private Message copyOf(Message src) {
        Message m = new Message();
        m.type = src.type;
        m.fromRole = src.fromRole;
        m.fromId = src.fromId;
        m.fromName = src.fromName;
        m.toId = src.toId;
        m.ts = src.ts;
        m.text = src.text;
        m.taskName = src.taskName;
        m.taskStatus = src.taskStatus;
        m.slotIndex = src.slotIndex;
        return m;
    }
}
