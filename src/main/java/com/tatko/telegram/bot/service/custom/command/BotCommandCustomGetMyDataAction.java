package com.tatko.telegram.bot.service.custom.command;

import com.tatko.telegram.bot.service.TelegramBotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Service
public class BotCommandCustomGetMyDataAction extends BotCommandCustom {

    /**
     * Constructor.
     */
    public BotCommandCustomGetMyDataAction() {
        super("GET_MY_DATA",
                "/getmydata", "get your data");
    }

    @Override
    public void doAction(final TelegramBotService telegramBotService, final Update update) {
        // NOP
    }

}
