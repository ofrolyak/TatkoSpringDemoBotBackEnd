package com.tatko.tatkospringdemobotbackend.service;

import com.tatko.tatkospringdemobotbackend.MockitoExtensionBaseMockTests;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

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
        Mockito.doNothing()
                .when(telegramBot)
                .sendMessage(ArgumentMatchers.anyLong(), ArgumentMatchers.anyString());

        // Action
        telegramBot.processReceivedMessage(update);

        // Then
        Mockito.verify(telegramBot, Mockito.never())
                .acceptBotCommandCustom(ArgumentMatchers.any(BotCommandCustom.class),
                        ArgumentMatchers.any(Update.class));
        Mockito.verify(telegramBot, Mockito.times(1))
                .sendMessage(ArgumentMatchers.anyLong(), ArgumentMatchers.anyString());
    }

    @Test
    void processReceivedMessage_ActionMode_Test() {

        // Before
        Update update = new Update();
        Message message = new Message();
        Chat chat = new Chat();
        chat.setId(gen.nextLong());
        message.setChat(chat);
        BotCommandCustom botCommandCustom
                = telegramBot.getBotCommandsSet().stream().findAny().get();
        message.setText(botCommandCustom.getMessageText());
        update.setMessage(message);

        // When
        Mockito.doNothing()
                .when(telegramBot)
                .acceptBotCommandCustom(ArgumentMatchers.any(BotCommandCustom.class),
                        ArgumentMatchers.any(Update.class));

        // Action
        telegramBot.processReceivedMessage(update);

        // Then
        Mockito.verify(telegramBot, Mockito.times(1))
                .acceptBotCommandCustom(ArgumentMatchers.any(BotCommandCustom.class),
                        ArgumentMatchers.any(Update.class));
        Mockito.verify(telegramBot, Mockito.never())
                .sendMessage(ArgumentMatchers.anyLong(), ArgumentMatchers.anyString());
    }

}