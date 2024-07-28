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
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * JUnit class for TelegramBot class sendMessage method (2 params).
 */
class TelegramBot4sendMessage42params4Test
        extends MockitoExtensionBaseMockTests {

    /**
     * TelegramBot instance with injected mocks.
     */
    @Spy
    @InjectMocks
    private TelegramBot telegramBot;


    @SneakyThrows
    @Test
    void sendMessage4Test() {

        // Before
        final long chatId = getGen().nextLong();
        final String message = getGen().nextString();

        // When
        Mockito.doReturn(null)
                .when(telegramBot)
                .execute(ArgumentMatchers.any(SendMessage.class));

        // Action
        telegramBot.sendMessage(chatId, message);

        // Then
        Mockito.verify(telegramBot, Mockito.times(1))
                .execute(ArgumentMatchers.any(SendMessage.class));
    }

    @Test
    void sendMessage4SneakyThrows4Test() throws TelegramApiException {

        // Before
        final long chatId = getGen().nextLong();
        final String message = getGen().nextString();

        Mockito.doThrow(TelegramApiException.class)
                .when(telegramBot)
                .execute(ArgumentMatchers.any(SendMessage.class));

        Assertions.assertThatThrownBy(()
                        -> telegramBot.sendMessage(chatId, message))
                .isInstanceOf(TelegramApiException.class);

    }


}
