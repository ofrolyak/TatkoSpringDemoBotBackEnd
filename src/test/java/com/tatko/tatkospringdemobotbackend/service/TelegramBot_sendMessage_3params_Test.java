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
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * JUnit class for TelegramBot class sendMessage method (3 params).
 */
class TelegramBot_sendMessage_3params_Test
        extends MockitoExtensionBaseMockTests {

    @Spy
    @InjectMocks
    private TelegramBot telegramBot;


    @SneakyThrows
    @Test
    void sendMessage4Test() {

        // Before
        long chatId = gen.nextLong();
        String message = gen.nextString();
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        // When
        Mockito.doReturn(null)
                .when(telegramBot)
                .execute(ArgumentMatchers.any(SendMessage.class));

        // Action
        telegramBot.sendMessage(chatId, message, replyKeyboardMarkup);

        // Then
        Mockito.verify(telegramBot, Mockito.times(1)).execute(ArgumentMatchers.any(SendMessage.class));
    }

    @Test
    void sendMessage4SneakyThrows4Test() throws TelegramApiException {

        // Before
        long chatId = gen.nextLong();
        String message = gen.nextString();
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        Mockito.doThrow(TelegramApiException.class)
                .when(telegramBot)
                .execute(ArgumentMatchers.any(SendMessage.class));

        Assertions.assertThatThrownBy(() -> telegramBot.sendMessage(
                chatId, message, replyKeyboardMarkup))
                .isInstanceOf(TelegramApiException.class);

    }

}
