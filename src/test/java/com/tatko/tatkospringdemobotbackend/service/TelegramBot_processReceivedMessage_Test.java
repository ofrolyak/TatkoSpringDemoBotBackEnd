package com.tatko.tatkospringdemobotbackend.service;

import com.tatko.tatkospringdemobotbackend.MockitoExtensionBaseMockTests;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class TelegramBot_processReceivedMessage_Test
        extends MockitoExtensionBaseMockTests {

    @Spy
    @InjectMocks
    TelegramBot telegramBot;


    @Test
    void processReceivedMessage_notActionMode_Test() {

        // Before
        Update update = new Update();
        Message message = new Message();
        Chat chat = new Chat();
        chat.setId(gen.nextLong());
        message.setChat(chat);
        message.setText("sth");
        update.setMessage(message);

        // When
        doNothing()
                .when(telegramBot)
                .sendMessage(anyLong(), anyString());

        // Action
        telegramBot.processReceivedMessage(update);

        // Then
        verify(telegramBot, never())
                .acceptBotCommandCustom(any(BotCommandCustom.class),
                        any(Update.class));
        verify(telegramBot, times(1))
                .sendMessage(anyLong(), anyString());
    }

    @Test
    void processReceivedMessage_ActionMode_Test() {

        // Before
        Update update = new Update();
        Message message = new Message();
        Chat chat = new Chat();
        chat.setId(gen.nextLong());
        message.setChat(chat);
        BotCommandCustom botCommandCustom = telegramBot.getBotCommandsSet().stream()
                .findAny().get();
        message.setText(botCommandCustom.getMessageText());
        update.setMessage(message);

        // When
        doNothing()
                .when(telegramBot)
                .acceptBotCommandCustom(any(BotCommandCustom.class), any(Update.class));

        // Action
        telegramBot.processReceivedMessage(update);

        // Then
        verify(telegramBot, times(1))
                .acceptBotCommandCustom(any(BotCommandCustom.class), any(Update.class));
        verify(telegramBot, never())
                .sendMessage(anyLong(), anyString());
    }

}