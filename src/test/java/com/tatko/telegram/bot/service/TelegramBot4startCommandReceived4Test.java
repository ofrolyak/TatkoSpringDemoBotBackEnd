package com.tatko.telegram.bot.service;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;

/**
 * JUnit class for TelegramBot class startCommandReceived method.
 */
class TelegramBot4startCommandReceived4Test
        extends MockitoExtensionBaseMockTests {

    /**
     * TelegramBot instance with injected mocks.
     */
    @Spy
    @InjectMocks
    private TelegramBot telegramBot;


    @Test
    void startCommandReceived4Test() {

        // When
        Mockito.doNothing()
                .when(telegramBot)
                .sendMessage(ArgumentMatchers.anyLong(),
                        ArgumentMatchers.anyString());

        // Action
        telegramBot.startCommandReceived(getGen().nextLong(), getGen().nextString());

        // Then
        Mockito.verify(telegramBot, Mockito.times(1))
                .startCommandReceived(ArgumentMatchers.anyLong(),
                        ArgumentMatchers.anyString());


    }
}
