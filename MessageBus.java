package com.gymapp.net;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class MessageBus {
    private static final CopyOnWriteArrayList<Consumer<Message>> listeners = new CopyOnWriteArrayList<>();
    public static void add(Consumer<Message> l) { listeners.add(l); }
    public static void remove(Consumer<Message> l) { listeners.remove(l); }
    public static void dispatch(Message m) { for (var l : listeners) l.accept(m); }
}
