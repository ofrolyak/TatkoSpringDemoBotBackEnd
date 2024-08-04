package com.tatko.telegram.bot.service;

@FunctionalInterface
public interface Operation {
    void execute(long a, String b);
}
