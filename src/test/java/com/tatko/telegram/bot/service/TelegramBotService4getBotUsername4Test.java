package com.tatko.telegram.bot.service;

import com.tatko.telegram.bot.SpringBootTestBaseMockTests;
import com.tatko.telegram.bot.config.TelegramBotConfig;
import com.tatko.telegram.bot.service.processor.TelegramBotProcessorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ContextConfiguration(classes = {
        TelegramBotService.class,
        })
class TelegramBotService4getBotUsername4Test extends SpringBootTestBaseMockTests {

    @MockBean
    TelegramBotConfig telegramBotConfigMock;
    @MockBean
    TelegramBotProcessorService telegramBotProcessorServiceMock;
    @Autowired
    private TelegramBotService telegramBotServiceMock;

    @Test
    void process4Test() {

        // Before
        String botUserName = getGen().nextString();

        // When
        doReturn(botUserName)
                .when(telegramBotConfigMock)
                .getTelegramBotName();

        // Action
        String botUsername = telegramBotServiceMock.getBotUsername();

        // Then
        assertThat(botUsername)
                .isEqualTo(botUserName);

    }

}