package com.tatko.telegram.bot.service.schedule;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.service.TelegramBotConfiguratorService;
import com.tatko.telegram.bot.service.business.AdService;
import com.tatko.telegram.bot.service.custom.operation.SendMessageOperation2Params;
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

class TelegramBotSchedulerAdService4processSendNextAd4Test extends MockitoExtensionBaseMockTests {

    @Mock
    TelegramBotConfiguratorService telegramBotConfiguratorService;
    @Mock
    AdService adService;
    @InjectMocks
    TelegramBotSchedulerAdService telegramBotSchedulerAdService;

    @Test
    void process4Test() {

        // When
        doNothing()
                .when(adService)
                .sendNextAd(any(SendMessageOperation2Params.class));
        when(telegramBotConfiguratorService
                .getOperationByClass(eq(SendMessageOperation2Params.class)))
                .thenReturn(mock(SendMessageOperation2Params.class));

        // Action
        telegramBotSchedulerAdService.sendNextAd();

        // Then
        verify(adService, times(1))
                .sendNextAd(any(SendMessageOperation2Params.class));
    }

}

