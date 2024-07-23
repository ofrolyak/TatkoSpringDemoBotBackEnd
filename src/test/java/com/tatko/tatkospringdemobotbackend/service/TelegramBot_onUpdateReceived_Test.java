package com.tatko.tatkospringdemobotbackend.service;

import com.tatko.tatkospringdemobotbackend.BaseMockTests;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class TelegramBot_onUpdateReceived_Test extends BaseMockTests {

    @Spy
    @InjectMocks
    TelegramBot telegramBot;

    @Test
    void onUpdateReceived_noMessage_Test() {

        // Before
        Update update = new Update();
        update.setMessage(null);

        // Action
        telegramBot.onUpdateReceived(update);

        // Then
        verify(telegramBot, never())
                .processReceivedMessage(any(Update.class));
    }

    @Test
    void onUpdateReceived_noMessageText_Test() {

        // Before
        Update update = new Update();
        Message message = new Message();
        message.setText(null);
        update.setMessage(message);

        // Action
        telegramBot.onUpdateReceived(update);

        // Then
        verify(telegramBot, never())
                .processReceivedMessage(any(Update.class));
    }

    @Test
    void onUpdateReceived_Test() {

        // Before
        Update update = new Update();
        Message message = new Message();
        message.setText(gen.nextString());
        update.setMessage(message);

        // When
        doNothing()
                .when(telegramBot)
                        .processReceivedMessage(any(Update.class));

        // Action
        telegramBot.onUpdateReceived(update);

        // Then
        verify(telegramBot, times(1))
                .processReceivedMessage(any(Update.class));
    }

}