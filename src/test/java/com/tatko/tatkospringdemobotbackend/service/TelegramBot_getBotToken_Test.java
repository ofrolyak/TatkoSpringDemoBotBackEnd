package com.tatko.tatkospringdemobotbackend.service;

import com.tatko.tatkospringdemobotbackend.BaseMockTests;
import com.tatko.tatkospringdemobotbackend.config.TelegramBotConfig;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class TelegramBot_getBotToken_Test extends BaseMockTests {

    @Mock
    TelegramBotConfig telegramBotConfig;
    @InjectMocks
    TelegramBot telegramBot;

    @Test
    void getBotToken_Test() {

        // Before
        String telegramBotToken = gen.nextString();

        // When
        when(telegramBotConfig.getTelegramBotToken())
                .thenReturn(telegramBotToken);

        // Then
        assertThat(telegramBot.getBotToken())
                .isEqualTo(telegramBotToken);

    }

}