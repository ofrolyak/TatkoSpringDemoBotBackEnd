package com.tatko.telegram.bot.service.custom.command;

import com.tatko.telegram.bot.service.custom.operation.SendMessageOperation2Params;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
public class BotCommandCustomStartAction extends BotCommandCustom {

    /**
     * Constructor.
     */
    public BotCommandCustomStartAction() {
        super("START", "/start", "get welcome message");
    }


    /**
     * React on START action.
     *
     * @param sendMessageOperation2Params
     * @param chatId                      Chat identifier in Telegram Bot.
     * @param name                        Name for Telegram user.
     */
    public void startCommandReceived(
            final SendMessageOperation2Params sendMessageOperation2Params,
            final long chatId,
            final String name) {

        log.info("Starting command received: {}", name);

        final String message = "Hi " + name + " from " + chatId
                + "! Nice to meet you!!!" + "\uD83C\uDF4C";

        sendMessageOperation2Params.execute(chatId, message);

        log.debug("Finished startCommandReceived");
    }

    /**
     * Do action for this action.
     *
     * @param update Received update from Telegram user.
     */
    @Override
    public void doAction(final Update update) {
        log.debug("doAction for update {}", update);
        final long chatId = update.getMessage().getChatId();
        final String firstName = update.getMessage().getChat().getFirstName();
        startCommandReceived(getTelegramBotConfiguratorService()
                        .getOperationByClass(
                                SendMessageOperation2Params.class),
                chatId, firstName);
        log.debug("Finished doAction for update {}", update);
    }

}
