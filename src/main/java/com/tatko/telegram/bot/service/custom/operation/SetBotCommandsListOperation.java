package com.tatko.telegram.bot.service.custom.operation;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

@FunctionalInterface
public interface SetBotCommandsListOperation extends OperationMarkerInterface {

    /**
     * Execute functional method.
     * @param botCommandList
     */
    void setBotCommandsList(List<BotCommand> botCommandList);

}
