package com.tatko.telegram.bot.util;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.config.TelegramBotConfig;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class BusinessUtility4isTelegramBotAdmin4Test extends MockitoExtensionBaseMockTests {

    @Mock
    TelegramBotConfig telegramBotConfig;
    @InjectMocks
    BusinessUtility businessUtility;

    @Test
    void isTelegramBotAdminIsTrue4Test() {

        // Before
        long chatId = getGen().nextInt();

        // When
        when(telegramBotConfig.getTelegramBotOwner())
                .thenReturn(chatId);

        // Action
        boolean telegramBotAdmin = businessUtility.isTelegramBotAdmin(chatId);

        // Then
        assertThat(telegramBotAdmin)
                .isTrue();

    }


    @Test
    void isTelegramBotAdminIsFalse4Test() {

        // Before
        long chatId = getGen().nextInt();

        // When
        when(telegramBotConfig.getTelegramBotOwner())
                .thenReturn((-1) * chatId);

        // Action
        boolean telegramBotAdmin = businessUtility.isTelegramBotAdmin(chatId);

        // Then
        assertThat(telegramBotAdmin)
                .isFalse();

    }

}