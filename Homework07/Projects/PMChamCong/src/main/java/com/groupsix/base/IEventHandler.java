package com.groupsix.base;

public interface IEventHandler<S, A> {
    void handle(S sender, A args);
}
