package com.tatko.telegram.bot.service.custom.command;

import com.tatko.telegram.bot.service.TelegramBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Service
public class BotCommandCustomSettingsAction extends BotCommandCustom {

    /**
     * Constructor.
     */
    public BotCommandCustomSettingsAction() {
        super(Action.SETTINGS,
                "/settings", "settings and preferences");
    }

    @Override
    public void doAction(final TelegramBot telegramBot, final Update update) {
        // NOP
    }

}
