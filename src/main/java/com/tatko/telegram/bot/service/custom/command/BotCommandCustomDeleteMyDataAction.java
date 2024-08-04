package com.tatko.telegram.bot.service.custom.command;

import com.tatko.telegram.bot.service.TelegramBotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component("botCommandCustomDeleteMyDataAction")
public class BotCommandCustomDeleteMyDataAction extends BotCommandCustom {

    /**
     * Constructor.
     */
    public BotCommandCustomDeleteMyDataAction() {
        super("DELETE_MY_DATA",
                "/deletemydata", "delete your data");
    }

    @Override
    public void doAction(final TelegramBotService telegramBotService, final Update update) {
        // NOP
    }

}
