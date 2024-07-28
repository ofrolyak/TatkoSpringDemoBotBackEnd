package com.tatko.telegram.bot.service;

import java.util.Collections;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
/**
 * JUnit class for TelegramBot class addCommandToBot method.
 */
class TelegramBot4addCommandToBot4Test
        extends MockitoExtensionBaseMockTests {

    /**
     * TelegramBot instance with injected mocks.
     */
    @Spy
    @InjectMocks
    private TelegramBot telegramBot;


    @SneakyThrows
    @Test
    void addPreparedBotCommandsToBot4Test() {

        // When
        Mockito.doReturn(Collections.emptyList())
                .when(telegramBot)
                .buildBotCommands();
        Mockito.doReturn(null)
                .when(telegramBot)
                .execute(ArgumentMatchers.any(SetMyCommands.class));

        // Action
        telegramBot.addPreparedBotCommandsToBot();

        // Then
        Mockito.verify(telegramBot, Mockito.times(1))
                .execute(ArgumentMatchers.any(SetMyCommands.class));

    }

    @Test
    void addPreparedBotCommandsToBot4SneakyThrows4Test()
            throws TelegramApiException {

        // Action
        Mockito.doThrow(TelegramApiException.class)
                .when(telegramBot)
                .execute(ArgumentMatchers.any(SetMyCommands.class));

        // Then
        Assertions.assertThatThrownBy(()
                        -> telegramBot.addPreparedBotCommandsToBot())
                .isInstanceOf(TelegramApiException.class);

    }


}
