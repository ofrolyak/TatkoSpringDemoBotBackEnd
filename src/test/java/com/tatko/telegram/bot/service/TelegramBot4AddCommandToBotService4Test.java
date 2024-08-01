package com.tatko.telegram.bot.service;

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

import java.util.Collections;
/**
 * JUnit class for TelegramBot class addCommandToBot method.
 */

class TelegramBot4AddCommandToBotService4Test
        extends MockitoExtensionBaseMockTests {

    /**
     * TelegramBot instance with injected mocks.
     */
    @Spy
    @InjectMocks
    private TelegramBotService telegramBotService;


    @SneakyThrows
    @Test
    void addPreparedBotCommandsToBot4Test() {

        // When
        Mockito.doReturn(Collections.emptyList())
                .when(telegramBotService)
                .buildBotCommands();
        Mockito.doReturn(null)
                .when(telegramBotService)
                .execute(ArgumentMatchers.any(SetMyCommands.class));

        // Action
        telegramBotService.addPreparedBotCommandsToBot();

        // Then
        Mockito.verify(telegramBotService, Mockito.times(1))
                .execute(ArgumentMatchers.any(SetMyCommands.class));

    }

    @Test
    void addPreparedBotCommandsToBot4SneakyThrows4Test()
            throws TelegramApiException {

        // Action
        Mockito.doThrow(TelegramApiException.class)
                .when(telegramBotService)
                .execute(ArgumentMatchers.any(SetMyCommands.class));

        // Then
        Assertions.assertThatThrownBy(()
                        -> telegramBotService.addPreparedBotCommandsToBot())
                .isInstanceOf(TelegramApiException.class);

    }


}
