package com.tatko.tatkospringdemobotbackend.service;

import com.tatko.tatkospringdemobotbackend.MockitoExtensionBaseMockTests;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
        doReturn(null)
                .when(telegramBot)
                .execute(any(SendMessage.class));

        // Action
        telegramBot.sendMessage(chatId, message);

        // Then
        verify(telegramBot, times(1)).execute(any(SendMessage.class));
    }

    @Test
    void sendMessage_SneakyThrows_Test() throws TelegramApiException {

        // Before
        long chatId = gen.nextLong();
        String message = gen.nextString();

        doThrow(TelegramApiException.class)
                .when(telegramBot)
                .execute(any(SendMessage.class));

        assertThatThrownBy(() -> telegramBot.sendMessage(chatId, message))
                .isInstanceOf(TelegramApiException.class);

    }


}