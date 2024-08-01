package com.tatko.telegram.bot.service.custom.command;

import com.tatko.telegram.bot.constant.Constant;
import com.tatko.telegram.bot.service.KeyboardMarkupHolder;
import com.tatko.telegram.bot.service.TelegramBotService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Service
public class BotCommandCustomRegisterAction extends BotCommandCustom {

    /**
     * Constructor.
     */
    public BotCommandCustomRegisterAction() {
        super(Action.REGISTER,
                "/register", "register user");
    }

    /**
     * Create SendMessage instance.
     *
     * @param chatId Identifier for Telegram Chat.
     * @param text Message text.
     * @param replyKeyboard Keyboard.
     *
     * @return SendMessage instance.
     */
    SendMessage buildSendMessage(final long chatId, final String text,
                                 final ReplyKeyboard replyKeyboard) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(replyKeyboard)
                .build();
    }

    /**
     * Process register action.
     *
     * @param update Prepared update instance for Telegram chat.
     */
    @SneakyThrows
    @Retryable(retryFor = TelegramApiException.class, maxAttempts = 2,
            backoff = @Backoff(delay = Constant.RETRYABLE_BACKOFF_DELAY))
    @Override
    public void doAction(final TelegramBotService telegramBotService, final Update update) {


        final long chatId = update.getMessage().getChat().getId();

        telegramBotService.execute(buildSendMessage(chatId,
                "Do you really want to register?",
                KeyboardMarkupHolder.INLINE_KEYBOARD_MARKUP));


    }

}
