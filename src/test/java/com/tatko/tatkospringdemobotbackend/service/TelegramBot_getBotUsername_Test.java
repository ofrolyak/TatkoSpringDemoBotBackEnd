package com.tatko.tatkospringdemobotbackend.service;

import com.tatko.tatkospringdemobotbackend.MockitoExtensionBaseMockTests;
import com.tatko.tatkospringdemobotbackend.config.TelegramBotConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

/**
 * JUnit class for TelegramBot class getBotUsername method.
 */
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
        Mockito.when(telegramBotConfig.getTelegramBotName())
                .thenReturn(telegramBotName);

        // Then
        Assertions.assertThat(telegramBot.getBotUsername())
                .isEqualTo(telegramBotName);

    }
}