package com.tatko.tatkospringdemobotbackend.service;

import java.util.Optional;

public enum Action {

    START("/start"),
    HELP("/help");

    public final String action;

    Action(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return action;
    }

    public static Optional<Action> fromString(String text) {
        for (Action b : Action.values()) {
            if (b.action.equalsIgnoreCase(text)) {
                return Optional.of(b);
            }
        }
        return Optional.empty();
    }
}
