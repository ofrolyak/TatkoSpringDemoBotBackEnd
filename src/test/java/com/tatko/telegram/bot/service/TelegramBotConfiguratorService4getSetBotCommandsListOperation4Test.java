package com.tatko.telegram.bot.service;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.service.custom.operation.OperationMarkerInterface;
import com.tatko.telegram.bot.service.custom.operation.SetBotCommandsListOperation;
import com.tatko.telegram.bot.service.custom.storage.ExecutorMapStorage;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class TelegramBotConfiguratorService4getSetBotCommandsListOperation4Test
        extends MockitoExtensionBaseMockTests {

    @Mock
    ExecutorMapStorage executorMapStorage;
    @Spy
    @InjectMocks
    TelegramBotConfiguratorService telegramBotConfiguratorService;

    @Test
    void process4Test() {

        // When
        Map<Class<? extends OperationMarkerInterface>,
                OperationMarkerInterface> operationMarkers = Map.of();
        SetBotCommandsListOperation setBotCommandsListOperation
                = mock(SetBotCommandsListOperation.class);

        // When
        doReturn(operationMarkers)
                .when(executorMapStorage)
                        .getExecutorMap();
        doReturn(setBotCommandsListOperation)
                .when(telegramBotConfiguratorService)
                .getOperationByClass(eq(operationMarkers),
                        eq(SetBotCommandsListOperation.class));

        // Action
        SetBotCommandsListOperation getSetBotCommandsListOperation
                = telegramBotConfiguratorService.getSetBotCommandsListOperation();

        // Then
        assertThat(getSetBotCommandsListOperation)
                .isEqualTo(setBotCommandsListOperation);

    }

}
