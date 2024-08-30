package com.tatko.telegram.bot.service;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.service.custom.operation.OperationMarkerInterface;
import com.tatko.telegram.bot.service.custom.operation.SendMessageOperation1Param;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.mock;

class TelegramBotConfiguratorService4getOperationByClass2Params4Test
        extends MockitoExtensionBaseMockTests {

    @InjectMocks
    TelegramBotConfiguratorService telegramBotConfiguratorService;

    @Test
    void failure4IllegalArgumentException4Test() {

        // When
        Map<Class<? extends OperationMarkerInterface>,
                OperationMarkerInterface> operationMarkers = Map.of();

        // Action and Then
        assertThatCode(() -> telegramBotConfiguratorService
                .getOperationByClass(operationMarkers,
                        SendMessageOperation1Param.class))
                .isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    void process4Test() {

        // When
        SendMessageOperation1Param sendMessageOperation1Param
                = mock(SendMessageOperation1Param.class);
        Map<Class<? extends OperationMarkerInterface>,
                OperationMarkerInterface> operationMarkers
                = Map.of(SendMessageOperation1Param.class,
                sendMessageOperation1Param);

        // Action
        SendMessageOperation1Param operationByClass
                = telegramBotConfiguratorService
                .getOperationByClass(operationMarkers,
                        SendMessageOperation1Param.class);

        // Then
        assertThat(operationByClass)
                .isEqualTo(sendMessageOperation1Param);

    }

}