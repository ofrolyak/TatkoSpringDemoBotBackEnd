package com.tatko.tatkospringdemobotbackend.service;

import com.tatko.tatkospringdemobotbackend.BaseMockTests;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class TelegramBot_sendMessage_3params_Test extends BaseMockTests {

    @Spy
    @InjectMocks
    TelegramBot telegramBot;

    @SneakyThrows
    @Test
    void sendMessage_Test() {

        // Before
        long chatId = gen.nextLong();
        String message = gen.nextString();
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        // When
        doReturn(null)
                .when(telegramBot)
                .execute(any(SendMessage.class));

        // Action
        telegramBot.sendMessage(chatId, message, replyKeyboardMarkup);

        // Then
        verify(telegramBot, times(1)).execute(any(SendMessage.class));
    }
}