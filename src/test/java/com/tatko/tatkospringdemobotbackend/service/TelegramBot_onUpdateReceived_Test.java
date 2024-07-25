package com.tatko.tatkospringdemobotbackend.service;

import com.tatko.tatkospringdemobotbackend.MockitoExtensionBaseMockTests;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * JUnit class for TelegramBot class onUpdateReceived method.
 */
class TelegramBot_onUpdateReceived_Test extends MockitoExtensionBaseMockTests {

    /**
     * TelegramBot instance with injected mocks.
     */
    @Spy
    @InjectMocks
    private TelegramBot telegramBot;


    @Test
    void onUpdateReceived4noMessage4Test() {

        // Before
        final Update update = new Update();
        update.setMessage(null);

        // Action
        telegramBot.onUpdateReceived(update);

        // Then
        Mockito.verify(telegramBot, Mockito.never())
                .processReceivedMessage(ArgumentMatchers.any(Update.class));
    }

    @Test
    void onUpdateReceived4noMessageText4Test() {

        // Before
        final Update update = new Update();
        final Message message = new Message();
        message.setText(null);
        update.setMessage(message);

        // Action
        telegramBot.onUpdateReceived(update);

        // Then
        Mockito.verify(telegramBot, Mockito.never())
                .processReceivedMessage(ArgumentMatchers.any(Update.class));
    }

    @Test
    void onUpdateReceived4Test() {

        // Before
        final Update update = new Update();
        final Message message = new Message();
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
