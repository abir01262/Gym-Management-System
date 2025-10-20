package com.gymapp.net;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class PersistenceManager {
    private static final File DIR = new File("data");
    private static final File PUB = new File(DIR, "public_chat.jsonl");
    private static final File NOTICES = new File(DIR, "notices.jsonl");
    private static final File PRIV = new File(DIR, "private_chats.jsonl");
    private static final File TASKS = new File(DIR, "assignment_progress.jsonl");

    static { if (!DIR.exists()) DIR.mkdirs(); }

    private static synchronized void append(File f, Message m) {
        try (BufferedWriter w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f, true), StandardCharsets.UTF_8))) {
            w.write(JsonUtil.toJson(m));
            w.write("\n");
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    public static void savePublic(Message m)  { append(PUB, m); }
    public static void saveNotice(Message m)  { append(NOTICES, m); }
    public static void savePrivate(Message m) { append(PRIV, m); }
    public static void saveTask(Message m)    { append(TASKS, m); }

    private static List<Message> readAll(File f) {
        try {
            if (!f.exists()) return List.of();
            List<String> lines = Files.readAllLines(f.toPath(), StandardCharsets.UTF_8);
            List<Message> out = new ArrayList<>();
            for (String l : lines) if (!l.isBlank()) out.add(JsonUtil.fromJson(l, Message.class));
            return out;
        } catch (Exception e) { return List.of(); }
    }

    public static List<Message> readPublic()  { return readAll(PUB); }
    public static List<Message> readNotices() { return readAll(NOTICES); }
    public static List<Message> readPrivate() { return readAll(PRIV); }
    public static List<Message> readTasks()   { return readAll(TASKS); }
}
