package com.tatko.telegram.bot.service;

import com.tatko.telegram.bot.SpringBootTestBaseMockTests;
import com.tatko.telegram.bot.config.TelegramBotConfig;
import com.tatko.telegram.bot.service.processor.TelegramBotProcessorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = {
        TelegramBotService.class,
})
class TelegramBotService4onUpdateReceived4Test extends SpringBootTestBaseMockTests {

    @MockBean
    TelegramBotConfig telegramBotConfigMock;
    @MockBean
    TelegramBotProcessorService telegramBotProcessorServiceMock;
    @Autowired
    private TelegramBotService telegramBotServiceMock;

    @Test
    void process4Test() {

        // Before
        Update update = getGen().nextUpdate();

        // When
        doNothing()
                .when(telegramBotProcessorServiceMock)
                .onUpdateReceived(eq(update));

        // Action
        telegramBotServiceMock.onUpdateReceived(update);

        // Then
        verify(telegramBotProcessorServiceMock, times(1))
                .onUpdateReceived(eq(update));

    }

}