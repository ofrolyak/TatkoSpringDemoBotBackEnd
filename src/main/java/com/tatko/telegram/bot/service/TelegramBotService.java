package com.tatko.telegram.bot.service;

import com.tatko.telegram.bot.config.TelegramBotConfig;
import com.tatko.telegram.bot.constant.Constant;
import com.tatko.telegram.bot.service.processor.TelegramBotProcessorService;
import com.tatko.telegram.bot.util.StaticUtility;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

/**
 * Telegram Bot service.
 */

@Component
@Slf4j
public class TelegramBotService extends TelegramLongPollingBot {

    /**
     * Constructor itself.
     * @param botToken
     */
    public TelegramBotService(
            final @Value("${telegram.bot.token}") String botToken) {
        super(botToken);
    }

    /**
     * Autowired by Spring TelegramBotProcessorService bean.
     */
    @Autowired
    private TelegramBotProcessorService telegramBotProcessorService;

    /**
     * TelegramBotConfig itself .
     */
    @Autowired
    private TelegramBotConfig telegramBotConfig;

    /**
     * Get Bot UserName.
     *
     * @return Bot UserName
     */
    @Override
    public String getBotUsername() {
        return telegramBotConfig.getTelegramBotName();
    }

    /**
     * Send message without keyboard.
     *
     * @param chatId  Identifier for Telegram chat.
     * @param message Prepared message instance for Telegram chat.
     */
    @SneakyThrows
    @Retryable(retryFor = TelegramApiException.class, maxAttempts = 2,
            backoff = @Backoff(delay = Constant.RETRYABLE_BACKOFF_DELAY))
    public void sendMessage(final long chatId, final String message) {

        log.info("Sending message: {} for chatId: {}", message, chatId);

        execute(StaticUtility.buildSendMessage(chatId, message));
    }

    /**
     * Send message to Telegram Bot.
     * @param sendMessage
     */
    @SneakyThrows
    @Retryable(retryFor = TelegramApiException.class, maxAttempts = 2,
            backoff = @Backoff(delay = Constant.RETRYABLE_BACKOFF_DELAY))
    public void sendMessage(final SendMessage sendMessage) {

        log.info("Sending sendMessage: {}", sendMessage);

        execute(sendMessage);

    }

    /**
     * Send message with keyboard.
     *
     * @param chatId              Identifier for Telegram chat.
     * @param message             Prepared message instance for Telegram chat.
     * @param replyKeyboardMarkup ReplyKeyboardMarkup instance.
     */
    @SneakyThrows
    @Retryable(retryFor = TelegramApiException.class, maxAttempts = 2,
            backoff = @Backoff(delay = Constant.RETRYABLE_BACKOFF_DELAY))
    public void sendMessage(final long chatId, final String message,
                            final ReplyKeyboardMarkup replyKeyboardMarkup) {

        log.info("Sending message: {} for chatId: {}", message, chatId);

        execute(StaticUtility.buildSendMessage(
                chatId, message, replyKeyboardMarkup));

    }

    /**
     * Init Bot command list for Telegram Bot.
     * @param botCommandList
     */
    @SneakyThrows
    @Retryable(retryFor = TelegramApiException.class, maxAttempts = 2,
            backoff = @Backoff(delay = Constant.RETRYABLE_BACKOFF_DELAY))
    public void setBotCommandsList(final List<BotCommand> botCommandList) {

        execute(new SetMyCommands(botCommandList, new BotCommandScopeDefault(),
                null));
    }

    /**
     * Enter point for Telegram Bot activity.
     * @param update Update received
     */
    @Override
    public void onUpdateReceived(final Update update) {

        log.info("Received update: {}", update);

        telegramBotProcessorService.onUpdateReceived(update);
    }


}
