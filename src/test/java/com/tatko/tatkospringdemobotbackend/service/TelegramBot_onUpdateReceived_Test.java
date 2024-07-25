package com.tatko.tatkospringdemobotbackend.service;

import com.tatko.tatkospringdemobotbackend.MockitoExtensionBaseMockTests;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

class TelegramBot_onUpdateReceived_Test extends MockitoExtensionBaseMockTests {

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
        Mockito.verify(telegramBot, Mockito.never())
                .processReceivedMessage(ArgumentMatchers.any(Update.class));
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
        Mockito.verify(telegramBot, Mockito.never())
                .processReceivedMessage(ArgumentMatchers.any(Update.class));
    }

    @Test
    void onUpdateReceived_Test() {

        // Before
        Update update = new Update();
        Message message = new Message();
        message.setText(gen.nextString());
        update.setMessage(message);

        // When
        Mockito.doNothing()
                .when(telegramBot)
                        .processReceivedMessage(ArgumentMatchers.any(Update.class));

        // Action
        telegramBot.onUpdateReceived(update);

        // Then
        Mockito.verify(telegramBot, Mockito.times(1))
                .processReceivedMessage(ArgumentMatchers.any(Update.class));
    }

}