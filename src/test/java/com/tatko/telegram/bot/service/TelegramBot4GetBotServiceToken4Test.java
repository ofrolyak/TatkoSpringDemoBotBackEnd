package com.tatko.telegram.bot.service;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.config.TelegramBotConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
/**
 * JUnit class for TelegramBot class getBotToken method.
 */

class TelegramBot4GetBotServiceToken4Test extends MockitoExtensionBaseMockTests {

    /**
     * Mock for TelegramBotConfig
     */
    @Mock
    private TelegramBotConfig telegramBotConfig;

    /**
     * TelegramBot instance with injected mocks.
     */
    @Spy
    @InjectMocks
    private TelegramBotService telegramBotService;

    @Test
    void getBotToken4Test() {

        // Before
        final String telegramBotToken = getGen().nextString();

        // When
        Mockito.when(telegramBotConfig.getTelegramBotToken())
                .thenReturn(telegramBotToken);

        // Then
        Assertions.assertThat(telegramBotService.getBotToken())
                .isEqualTo(telegramBotToken);

    }

}

