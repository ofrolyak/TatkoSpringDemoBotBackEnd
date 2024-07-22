package com.tatko.tatkospringdemobotbackend.service;

import com.tatko.tatkospringdemobotbackend.BaseMockTests;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TelegramBot_sendMessage_Test extends BaseMockTests {

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
}