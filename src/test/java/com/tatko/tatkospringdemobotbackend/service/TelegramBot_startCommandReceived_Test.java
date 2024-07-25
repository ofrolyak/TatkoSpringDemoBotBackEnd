package com.tatko.tatkospringdemobotbackend.service;

import com.tatko.tatkospringdemobotbackend.MockitoExtensionBaseMockTests;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;

/**
 * JUnit class for TelegramBot class startCommandReceived method.
 */
class TelegramBot_startCommandReceived_Test
        extends MockitoExtensionBaseMockTests {

    @Spy
    @InjectMocks
    private TelegramBot telegramBot;


    @Test
    void startCommandReceived4Test() {

        // When
        Mockito.doNothing()
                .when(telegramBot)
                .sendMessage(ArgumentMatchers.anyLong(), ArgumentMatchers.anyString());

        // Action
        telegramBot.startCommandReceived(gen.nextLong(), gen.nextString());

        // Then
        Mockito.verify(telegramBot, Mockito.times(1))
                .startCommandReceived(ArgumentMatchers.anyLong(), ArgumentMatchers.anyString());


    }
}
