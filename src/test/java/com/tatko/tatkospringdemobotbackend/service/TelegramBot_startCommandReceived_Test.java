package com.tatko.tatkospringdemobotbackend.service;

import com.tatko.tatkospringdemobotbackend.BaseMockTests;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class TelegramBot_startCommandReceived_Test extends BaseMockTests {

    @Spy
    @InjectMocks
    TelegramBot telegramBot;

    @Test
    void startCommandReceived_Test() {

        // When
        doNothing()
                .when(telegramBot)
                .sendMessage(anyLong(), anyString());

        // Action
        telegramBot.startCommandReceived(gen.nextLong(), gen.nextString());

        // Then
        verify(telegramBot, times(1)).startCommandReceived(anyLong(), anyString());


    }
}