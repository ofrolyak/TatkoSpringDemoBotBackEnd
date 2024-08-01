package com.tatko.telegram.bot.service;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
/**
 * JUnit class for TelegramBot class sendMessage method (3 params).
 */

class TelegramBotService4SendMessage43Params4Test
        extends MockitoExtensionBaseMockTests {

    /**
     * TelegramBot instance with injected mocks.
     */
    @Spy
    @InjectMocks
    private TelegramBotService telegramBotService;


    @SneakyThrows
    @Test
    void sendMessage4Test() {

        // Before
        final long chatId = getGen().nextLong();
        final String message = getGen().nextString();
        final ReplyKeyboardMarkup replyKeyboardMarkup
                = new ReplyKeyboardMarkup();

        // When
        Mockito.doReturn(null)
                .when(telegramBotService)
                .execute(ArgumentMatchers.any(SendMessage.class));

        // Action
        telegramBotService.sendMessage(chatId, message, replyKeyboardMarkup);

        // Then
        Mockito.verify(telegramBotService, Mockito.times(1))
                .execute(ArgumentMatchers.any(SendMessage.class));
    }

    @Test
    void sendMessage4SneakyThrows4Test() throws TelegramApiException {

        // Before
        final long chatId = getGen().nextLong();
        final String message = getGen().nextString();
        final ReplyKeyboardMarkup replyKeyboardMarkup
                = new ReplyKeyboardMarkup();

        Mockito.doThrow(TelegramApiException.class)
                .when(telegramBotService)
                .execute(ArgumentMatchers.any(SendMessage.class));

        Assertions.assertThatThrownBy(() -> telegramBotService.sendMessage(
                chatId, message, replyKeyboardMarkup))
                .isInstanceOf(TelegramApiException.class);

    }

}
