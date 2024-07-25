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
 * JUnit class for TelegramBot class getBotToken method.
 */
class TelegramBot_getBotToken_Test extends MockitoExtensionBaseMockTests {

    @Spy
    @InjectMocks
    TelegramBot telegramBot;


    @Mock
    TelegramBotConfig telegramBotConfig;

    @Test
    void getBotToken4Test() {

        // Before
        final String telegramBotToken = gen.nextString();

        // When
        Mockito.when(telegramBotConfig.getTelegramBotToken())
                .thenReturn(telegramBotToken);

        // Then
        Assertions.assertThat(telegramBot.getBotToken())
                .isEqualTo(telegramBotToken);

    }

}

