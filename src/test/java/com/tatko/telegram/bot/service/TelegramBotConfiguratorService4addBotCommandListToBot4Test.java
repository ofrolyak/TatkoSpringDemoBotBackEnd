package com.tatko.telegram.bot.service;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.service.custom.operation.SetBotCommandsListOperation;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class TelegramBotConfiguratorService4addBotCommandListToBot4Test
        extends MockitoExtensionBaseMockTests {

    @InjectMocks
    private TelegramBotConfiguratorService telegramBotConfiguratorService;

    @Test
    void process4Test() {

        // Before
        final List<BotCommand> botCommandList = List.of();
        final SetBotCommandsListOperation setBotCommandsListOperation
                = mock(SetBotCommandsListOperation.class);

        // Action
        telegramBotConfiguratorService.addBotCommandListToBot(
                botCommandList, setBotCommandsListOperation);

        // Then
        verify(setBotCommandsListOperation, times(1))
                .setBotCommandsList(eq(botCommandList));

    }

}
