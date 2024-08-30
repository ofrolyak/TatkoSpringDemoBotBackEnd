package com.tatko.telegram.bot.audit;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.service.TelegramBotConfiguratorService;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OnUpdateInitProcessor4doAround4Test extends MockitoExtensionBaseMockTests {

    @Mock
    ProceedingJoinPoint proceedingJoinPoint;
    @Mock
    private TelegramBotConfiguratorService telegramBotConfiguratorService;
    @InjectMocks
    private OnUpdateInitProcessor onUpdateInitProcessor;

    @SneakyThrows
    @Test
    void success() {

        // When
        when(proceedingJoinPoint.getArgs())
                .thenReturn(new Object[]{new Update()});
        doNothing()
                .when(telegramBotConfiguratorService)
                .configureServiceData(any(Update.class));

        // Action
        onUpdateInitProcessor.doAround(proceedingJoinPoint);

        // Then
        verify(telegramBotConfiguratorService, times(1))
                .configureServiceData(any(Update.class));

    }

}