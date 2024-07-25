package com.tatko.tatkospringdemobotbackend.service;

import com.tatko.tatkospringdemobotbackend.MockitoExtensionBaseMockTests;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class TelegramBot_addCommandToBot_Test
        extends MockitoExtensionBaseMockTests {

    @Spy
    @InjectMocks
    TelegramBot telegramBot;


    @SneakyThrows
    @Test
    void addPreparedBotCommandsToBot_Test() {

        // When
        doReturn(Collections.emptyList())
                .when(telegramBot)
                        .buildBotCommands();
        doReturn(null)
                .when(telegramBot)
                        .execute(any(SetMyCommands.class));

        // Action
        telegramBot.addPreparedBotCommandsToBot();

        // Then
        verify(telegramBot, times(1)).execute(any(SetMyCommands.class));

    }

    @Test
    void addPreparedBotCommandsToBot_SneakyThrows_Test()
            throws TelegramApiException {

        // Action
        doThrow(TelegramApiException.class)
                .when(telegramBot)
                .execute(any(SetMyCommands.class));

        // Then
        assertThatThrownBy(() -> telegramBot.addPreparedBotCommandsToBot())
                .isInstanceOf(TelegramApiException.class);

    }


}