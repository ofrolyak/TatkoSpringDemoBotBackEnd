package com.tatko.tatkospringdemobotbackend.service;

import com.tatko.tatkospringdemobotbackend.MockitoExtensionBaseMockTests;
import com.tatko.tatkospringdemobotbackend.config.TelegramBotConfig;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class TelegramBot_getBotUsername_Test extends MockitoExtensionBaseMockTests {

    @Spy
    @InjectMocks
    TelegramBot telegramBot;


    @Mock
    TelegramBotConfig telegramBotConfig;

    @Test
    void getBotUsername_Test() {

        // Before
        String telegramBotName = gen.nextString();

        // When
        when(telegramBotConfig.getTelegramBotName())
                .thenReturn(telegramBotName);

        // Then
        assertThat(telegramBot.getBotUsername())
                .isEqualTo(telegramBotName);

    }
}