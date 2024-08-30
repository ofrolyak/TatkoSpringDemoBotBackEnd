package com.tatko.telegram.bot.service;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.service.custom.operation.SetBotCommandsListOperation;
import com.tatko.telegram.bot.service.custom.storage.BotCommandCustomSetStorage;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class TelegramBotConfiguratorService4addPreparedBotCommandsToBot4Test extends MockitoExtensionBaseMockTests {

    @Mock
    BotCommandCustomSetStorage botCommandCustomSetStorage;
    @Spy
    @InjectMocks
    TelegramBotConfiguratorService telegramBotConfiguratorService;

    @Test
    void process4Test() {

        // Before
        List<BotCommand> botCommandList
                = List.of(mock(BotCommand.class));
        SetBotCommandsListOperation setBotCommandsListOperation
                = mock(SetBotCommandsListOperation.class);
        // When
        doReturn(setBotCommandsListOperation)
                .when(telegramBotConfiguratorService)
                .getSetBotCommandsListOperation();
        doReturn(botCommandList)
                .when(botCommandCustomSetStorage)
                .getBotCommandList();
        doNothing()
                .when(telegramBotConfiguratorService)
                .addBotCommandListToBot(eq(botCommandList),
                        eq(setBotCommandsListOperation));

        // Action
        telegramBotConfiguratorService.addPreparedBotCommandsToBot();

        // Then
        verify(telegramBotConfiguratorService)
                .getSetBotCommandsListOperation();
        verify(botCommandCustomSetStorage)
                .getBotCommandList();
        verify(telegramBotConfiguratorService)
                .addBotCommandListToBot(eq(botCommandList),
                        eq(setBotCommandsListOperation));
    }

}
