package com.tatko.tatkospringdemobotbackend.service;

public enum Action {

    START,
    GET_MY_DATA,
    DELETE_MY_DATA,
    HELP,
    SETTINGS
//
//    private final String action;
//
//    Action(String action) {
//        this.action = action;
//    }
//
//    @Override
//    public String toString() {
//        return action;
//    }
//
//    public static Optional<Action> fromString(String text) {
//        for (Action b : Action.values()) {
//            if (b.action.equalsIgnoreCase(text)) {
//                return Optional.of(b);
//            }
//        }
//        return Optional.empty();
//    }
}
