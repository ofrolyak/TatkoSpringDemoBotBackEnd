package com.tatko.telegram.bot.service.custom.operation;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@FunctionalInterface
public interface SendMessageOperation3Params extends OperationMarkerInterface {

    /**
     * Execute functional method.
     * @param a
     * @param b
     * @param replyKeyboardMarkup
     */
    void execute(long a, String b, ReplyKeyboardMarkup replyKeyboardMarkup);

}
