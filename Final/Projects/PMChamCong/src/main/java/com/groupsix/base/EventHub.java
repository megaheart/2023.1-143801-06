package com.groupsix.base;

import java.util.ArrayList;

public class EventHub<S, A> {
    private final ArrayList<IEventHandler<S, A>> handlers = new ArrayList<>();

    public EventHub() {
    }

    public void subscribe(IEventHandler<S, A> handler) {
        if (handler == null) {
            throw new IllegalArgumentException("handler");
        }
        this.handlers.add(handler);
    }

    public void unsubscribe(IEventHandler<S, A> handler) {
        if (handler == null) {
            throw new IllegalArgumentException("handler");
        }
        this.handlers.remove(handler);
    }

    public void publish(S sender, A args) {
        for (IEventHandler<S, A> handler : this.handlers) {
            handler.handle(sender, args);
        }
    }
}
