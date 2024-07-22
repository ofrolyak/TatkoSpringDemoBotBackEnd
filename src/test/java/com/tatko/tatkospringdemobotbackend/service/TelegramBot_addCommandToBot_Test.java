package com.tatko.tatkospringdemobotbackend.service;

import com.tatko.tatkospringdemobotbackend.BaseMockTests;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TelegramBot_addCommandToBot_Test extends BaseMockTests {

    @Spy
    @InjectMocks
    TelegramBot telegramBot;

    @SneakyThrows
    @Test
    void addCommandToBot_Test() {

        // When
        doReturn(Collections.emptyList())
                .when(telegramBot)
                        .buildBotCommands();
        doReturn(null)
                .when(telegramBot)
                        .execute(any(SetMyCommands.class));

        // Action
        telegramBot.addCommandToBot();

        // Then
        verify(telegramBot, times(1)).execute(any(SetMyCommands.class));

    }
}