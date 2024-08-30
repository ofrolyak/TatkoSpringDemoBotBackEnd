package com.tatko.telegram.bot.service.schedule;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.service.TelegramBotConfiguratorService;
import com.tatko.telegram.bot.service.business.DateFactService;
import com.tatko.telegram.bot.service.custom.operation.SendMessageOperation1Param;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TelegramBotSchedulerDataFactService4processSend4Test extends MockitoExtensionBaseMockTests {

    @Mock
    private TelegramBotConfiguratorService telegramBotConfiguratorService;
    @Mock
    private DateFactService dateFactService;
    @InjectMocks
    private TelegramBotSchedulerDataFactService telegramBotSchedulerDataFactService;

    @Test
    void process4Test() {

        // When
        doNothing()
                .when(dateFactService)
                .sendNextDateFact(any(SendMessageOperation1Param.class));
        when(telegramBotConfiguratorService
                .getOperationByClass(eq(SendMessageOperation1Param.class)))
                .thenReturn(mock(SendMessageOperation1Param.class));

        // Action
        telegramBotSchedulerDataFactService.send();

        // Then
        verify(dateFactService, times(1))
                .sendNextDateFact(any(SendMessageOperation1Param.class));


    }

}