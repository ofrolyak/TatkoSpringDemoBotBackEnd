package com.tatko.telegram.bot.service.custom.operation;

@FunctionalInterface
public interface SendMessageOperation2Params extends OperationMarkerInterface {

    /**
     * Execute functional method.
     * @param a
     * @param b
     */
    void execute(long a, String b);

}
