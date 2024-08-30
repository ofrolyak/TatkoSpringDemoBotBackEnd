package com.tatko.telegram.bot.service.custom.storage;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.service.TelegramBotService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ExecutorMapStorage4getSetBotCommandsListOperation4Test
        extends MockitoExtensionBaseMockTests {

    @Mock
    private TelegramBotService telegramBotService;
    @InjectMocks
    private ExecutorMapStorage executorMapStorage;

    @Test
    void success4emptyCase4Test() {

        // When
        doNothing()
                .when(telegramBotService)
                .setBotCommandsList(anyList());

        // Action
        executorMapStorage.getSetBotCommandsListOperation().setBotCommandsList(Collections.emptyList());

        // Then
        verify(telegramBotService, times(1))
                .setBotCommandsList(anyList());

    }

    @Test
    void success4notEmptyCase4Test() {

        // When
        doNothing()
                .when(telegramBotService)
                .setBotCommandsList(anyList());

        // Action
        executorMapStorage.getSetBotCommandsListOperation().setBotCommandsList(List.of(new BotCommand()));

        // Then
        verify(telegramBotService, times(1))
                .setBotCommandsList(anyList());

    }

}