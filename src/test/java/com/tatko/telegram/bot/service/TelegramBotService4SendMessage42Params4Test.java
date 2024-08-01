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

class TelegramBotService4SendMessage42Params4Test
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

        // When
        Mockito.doReturn(null)
                .when(telegramBotService)
                .execute(ArgumentMatchers.any(SendMessage.class));

        // Action
        telegramBotService.sendMessage(chatId, message);

        // Then
        Mockito.verify(telegramBotService, Mockito.times(1))
                .execute(ArgumentMatchers.any(SendMessage.class));
    }

    @Test
    void sendMessage4SneakyThrows4Test() throws TelegramApiException {

        // Before
        final long chatId = getGen().nextLong();
        final String message = getGen().nextString();

        Mockito.doThrow(TelegramApiException.class)
                .when(telegramBotService)
                .execute(ArgumentMatchers.any(SendMessage.class));

        Assertions.assertThatThrownBy(()
                        -> telegramBotService.sendMessage(chatId, message))
                .isInstanceOf(TelegramApiException.class);

    }


}
