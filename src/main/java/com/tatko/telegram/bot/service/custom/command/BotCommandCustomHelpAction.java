package com.tatko.telegram.bot.service.custom.command;

import com.tatko.telegram.bot.service.TelegramBotService;
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
     * @param telegramBotService Telegram Bot instance.
     * @param update Received update from Telegram user.
     */
    @Override
    public void doAction(final TelegramBotService telegramBotService, final Update update) {
        final long chatId = update.getMessage().getChatId();
//        telegramBotService.sendMessage(chatId,
//                "This is bot for demonstration how to Spring Boot"
//                        + " works with Telegram.",
//                KeyboardMarkupHolder.REPLY_KEYBOARD_MARKUP_INSTANCE);
    }

}
