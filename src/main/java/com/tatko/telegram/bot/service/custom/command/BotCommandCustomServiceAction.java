package com.tatko.telegram.bot.service.custom.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class BotCommandCustomServiceAction extends BotCommandCustom {

    /**
     * Constructor.
     */
    public BotCommandCustomServiceAction() {
        super("SERVICE",
                "/service", "business service");
    }

    /**
     * Do action for this action.
     *
     * @param update Received update from Telegram user.
     */
    @Override
    public void doAction(final Update update) {
        super.addKeyboardAndSendMessage(update);
    }

}
