package com.tatko.telegram.bot.service.custom.operation;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@FunctionalInterface
public interface SendMessageOperation1Param extends OperationMarkerInterface {

    /**
     * Execute functional method.
     * @param sendMessage
     */
    void execute(SendMessage sendMessage);
}
