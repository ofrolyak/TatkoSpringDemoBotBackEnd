package com.tatko.telegram.bot.service.custom.command;

import com.tatko.telegram.bot.service.custom.operation.SendMessageOperation2Params;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Service
public class BotCommandCustomHelpAction extends BotCommandCustom {

    /**
     * Constructor.
     */
    public BotCommandCustomHelpAction() {
        super("HELP",
                "/help", "how to use this bot");
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

        getTelegramBotConfiguratorService().getOperationByClass(
                        SendMessageOperation2Params.class)
                .execute(chatId,
                        "This is bot for demonstration how to Spring Boot"
                                + " works with Telegram.");

        log.debug("Finished doAction for update {}", update);

    }

}
