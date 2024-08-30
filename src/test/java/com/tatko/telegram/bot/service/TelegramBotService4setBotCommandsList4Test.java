package com.tatko.telegram.bot.service;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class TelegramBotService4setBotCommandsList4Test
        extends MockitoExtensionBaseMockTests {

    @Spy
    @InjectMocks
    private TelegramBotService telegramBotService;

    @SneakyThrows
    @Test
    void process4emptyListCase4Test() {

        // Before
        final List<BotCommand> botCommandList = Collections.emptyList();

        // When
        doReturn(null)
                .when(telegramBotService)
                .execute(any(SetMyCommands.class));

        // Action
        telegramBotService.setBotCommandsList(botCommandList);

        // Then
        verify(telegramBotService, times(1))
                .execute(any(SetMyCommands.class));

    }

    @SneakyThrows
    @Test
    void process4notEmptyListCase4Test() {

        // Before
        final List<BotCommand> botCommandList = List.of(mock(BotCommand.class));

        // When
        doReturn(null)
                .when(telegramBotService)
                .execute(any(SetMyCommands.class));

        // Action
        telegramBotService.setBotCommandsList(botCommandList);

        // Then
        verify(telegramBotService, times(1))
                .execute(any(SetMyCommands.class));

    }

    @SneakyThrows
    @Test
    public void throwSneakyTelegramApiExceptionUsingLombok_TelegramApiExceptionShouldBeThrown() {

        // Before
        final List<BotCommand> botCommandList = List.of(mock(BotCommand.class));

        // When
        doThrow(TelegramApiException.class)
                .when(telegramBotService)
                .execute(any(SetMyCommands.class));

        // Then
        assertThatThrownBy(() -> telegramBotService.setBotCommandsList(botCommandList))
                .isInstanceOf(TelegramApiException.class)
                .hasStackTraceContaining("org.telegram.telegrambots.meta.exceptions.TelegramApiException");
    }

}

