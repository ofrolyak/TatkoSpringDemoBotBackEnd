package com.tatko.telegram.bot.service.custom.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
public class BotCommandCustomSettingsAction extends BotCommandCustom {

    /**
     * Constructor.
     */
    public BotCommandCustomSettingsAction() {
        super("SETTINGS", "/settings", "settings and preferences");
    }

    /**
     * Do action for this action.
     *
     * @param update Received update from Telegram user.
     */
    @Override
    public void doAction(final Update update) {
        log.debug("doAction for update {}", update);
        super.addKeyboardAndSendMessage(update);
        log.debug("Finished doAction for update {}", update);
    }

}
