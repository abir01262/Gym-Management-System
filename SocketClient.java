package com.gymapp.net;

import javafx.application.Platform;

import java.io.*;
import java.net.Socket;
import java.util.function.Consumer;

public class SocketClient {
    private Socket socket;
    private BufferedWriter out;
    private Thread reader;
    private Consumer<Message> onMessage;

    public void connect(Role role, String id, String name, Consumer<Message> handler) {
        try {
            SocketHub.get().start(); // ensure hub is running in-process
            socket = new Socket("127.0.0.1", SocketHub.PORT);
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            onMessage = handler;
            reader = new Thread(this::readLoop, "SocketClient-Reader");
            reader.setDaemon(true);
            reader.start();
            Message join = new Message();
            join.type = MessageType.SESSION_JOIN;
            join.fromRole = role;
            join.fromId = id;
            join.fromName = name;
            join.ts = System.currentTimeMillis();
            send(join);
        } catch (IOException e) { throw new RuntimeException(e); }
    }

    private void readLoop() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                Message m = JsonUtil.fromJson(line, Message.class);
                Consumer<Message> h = onMessage;
                if (h != null) Platform.runLater(() -> h.accept(m));
            }
        } catch (Exception ignored) { }
    }

    public void send(Message m) {
        try {
            out.write(JsonUtil.toJson(m));
            out.write("\n");
            out.flush();
        } catch (IOException e) { throw new RuntimeException(e); }
    }
}
