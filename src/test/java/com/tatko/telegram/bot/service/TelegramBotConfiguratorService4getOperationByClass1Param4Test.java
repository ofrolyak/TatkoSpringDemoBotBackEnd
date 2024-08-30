package com.tatko.telegram.bot.service;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.service.custom.operation.SendMessageOperation1Param;
import com.tatko.telegram.bot.service.custom.storage.ExecutorMapStorage;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

class TelegramBotConfiguratorService4getOperationByClass1Param4Test
        extends MockitoExtensionBaseMockTests {

    @Mock
    ExecutorMapStorage executorMapStorage;
    @Spy
    @InjectMocks
    TelegramBotConfiguratorService telegramBotConfiguratorService;

    @Test
    void failure4IllegalArgumentException4Test() {

        // When
        doThrow(new IllegalArgumentException())
                .when(telegramBotConfiguratorService)
                        .getOperationByClass(anyMap(),
                                eq(SendMessageOperation1Param.class));

        // Action and Then
        assertThatCode(() -> telegramBotConfiguratorService
                .getOperationByClass(SendMessageOperation1Param.class))
                .isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    void process4Test() {

        // Before
        SendMessageOperation1Param sendMessageOperation1Param
                = mock(SendMessageOperation1Param.class);

        // When
        doReturn(sendMessageOperation1Param)
                .when(telegramBotConfiguratorService)
                .getOperationByClass(anyMap(),
                        eq(SendMessageOperation1Param.class));

        // Action
        SendMessageOperation1Param operationByClass
                = telegramBotConfiguratorService
                .getOperationByClass(SendMessageOperation1Param.class);

        // Then
        assertThat(operationByClass)
                .isEqualTo(sendMessageOperation1Param);

    }

}