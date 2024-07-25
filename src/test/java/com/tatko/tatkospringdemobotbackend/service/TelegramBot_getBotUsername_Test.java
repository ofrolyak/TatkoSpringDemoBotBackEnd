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

    /**
     * Mock for TelegramBotConfig instance.
     */
    @Mock
    TelegramBotConfig telegramBotConfig;

    /**
     * TelegramBot instance with injected mocks.
     */
    @Spy
    @InjectMocks
    TelegramBot telegramBot;

    @Test
    void getBotUsername4Test() {

        // Before
        final String telegramBotName = gen.nextString();

        // When
        Mockito.when(telegramBotConfig.getTelegramBotName())
                .thenReturn(telegramBotName);

        // Then
        Assertions.assertThat(telegramBot.getBotUsername())
                .isEqualTo(telegramBotName);

    }
}
