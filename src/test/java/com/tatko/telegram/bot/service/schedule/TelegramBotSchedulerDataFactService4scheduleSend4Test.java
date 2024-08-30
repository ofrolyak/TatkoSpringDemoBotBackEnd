package com.tatko.telegram.bot.service.schedule;

import com.tatko.telegram.bot.SpringBootTestBaseMockTests;
import com.tatko.telegram.bot.config.ScheduledConfig;
import com.tatko.telegram.bot.service.TelegramBotConfiguratorService;
import com.tatko.telegram.bot.service.business.DateFactService;
import com.tatko.telegram.bot.service.custom.operation.SendMessageOperation1Param;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(ScheduledConfig.class)
class TelegramBotSchedulerDataFactService4scheduleSend4Test
        extends SpringBootTestBaseMockTests {

    @MockBean
    TelegramBotConfiguratorService telegramBotConfiguratorService;
    @MockBean
    DateFactService dateFactService;
    @SpyBean
    TelegramBotSchedulerDataFactService telegramBotSchedulerDataFactService;

    @Test
    void schedule4Test() {

        // When
        doNothing()
                .when(dateFactService)
                .sendNextDateFact(any(SendMessageOperation1Param.class));
        when(telegramBotConfiguratorService
                .getOperationByClass(eq(SendMessageOperation1Param.class)))
                .thenReturn(mock(SendMessageOperation1Param.class));

        // Then
        await()
                .atMost(Duration.ofSeconds(5))
                .untilAsserted(()
                        -> verify(telegramBotSchedulerDataFactService, atLeast(4)).send());

    }

}
