package com.tatko.telegram.bot.service;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class TelegramBotService4sendMessage3Params4Test
        extends MockitoExtensionBaseMockTests {

    @Spy
    @InjectMocks
    private TelegramBotService telegramBotService;

    @SneakyThrows
    @Test
    void process4Test() {

        // Before
        long chatId = getGen().nextLong();
        String textMessage = getGen().nextString();
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        // When
        doReturn(null)
                .when(telegramBotService)
                .execute(any(SendMessage.class));

        // Action
        telegramBotService.sendMessage(chatId, textMessage, replyKeyboardMarkup);

        // Then
        verify(telegramBotService, times(1))
                .execute(any(SendMessage.class));

    }

    @SneakyThrows
    @Test
    public void throwSneakyTelegramApiExceptionUsingLombok_TelegramApiExceptionShouldBeThrown() {

        // Before
        long chatId = getGen().nextLong();
        String textMessage = getGen().nextString();
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        // When
        doThrow(TelegramApiException.class)
                .when(telegramBotService)
                .execute(any(SendMessage.class));

        // Then
        assertThatThrownBy(() -> telegramBotService.sendMessage(chatId, textMessage, replyKeyboardMarkup))
                .isInstanceOf(TelegramApiException.class)
                .hasStackTraceContaining("org.telegram.telegrambots.meta.exceptions.TelegramApiException");
    }

}
