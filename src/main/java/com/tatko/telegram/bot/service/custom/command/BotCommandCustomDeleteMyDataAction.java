package com.tatko.telegram.bot.service.custom.command;

import com.tatko.telegram.bot.service.TelegramBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Service
public class BotCommandCustomDeleteMyDataAction extends BotCommandCustom {

    /**
     * Constructor.
     */
    public BotCommandCustomDeleteMyDataAction() {
        super(Action.DELETE_MY_DATA,
                "/deletemydata", "delete your data");
    }

    @Override
    public void doAction(final TelegramBot telegramBot, final Update update) {
        // NOP
    }

}
