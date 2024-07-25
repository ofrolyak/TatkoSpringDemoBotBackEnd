package com.tatko.tatkospringdemobotbackend.service;

import com.tatko.tatkospringdemobotbackend.MockitoExtensionBaseMockTests;
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
class TelegramBot_sendMessage_2params_Test
        extends MockitoExtensionBaseMockTests {

    @Spy
    @InjectMocks
    TelegramBot telegramBot;


    @SneakyThrows
    @Test
    void sendMessage_Test() {

        // Before
        long chatId = gen.nextLong();
        String message = gen.nextString();

        // When
        Mockito.doReturn(null)
                .when(telegramBot)
                .execute(ArgumentMatchers.any(SendMessage.class));

        // Action
        telegramBot.sendMessage(chatId, message);

        // Then
        Mockito.verify(telegramBot, Mockito.times(1)).execute(ArgumentMatchers.any(SendMessage.class));
    }

    @Test
    void sendMessage_SneakyThrows_Test() throws TelegramApiException {

        // Before
        long chatId = gen.nextLong();
        String message = gen.nextString();

        Mockito.doThrow(TelegramApiException.class)
                .when(telegramBot)
                .execute(ArgumentMatchers.any(SendMessage.class));

        Assertions.assertThatThrownBy(() -> telegramBot.sendMessage(chatId, message))
                .isInstanceOf(TelegramApiException.class);

    }


}