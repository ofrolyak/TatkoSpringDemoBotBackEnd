package com.tatko.tatkospringdemobotbackend.service;

import com.tatko.tatkospringdemobotbackend.MockitoExtensionBaseMockTests;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.Consumer;

/**
 * JUnit class for TelegramBot class acceptBotCommandCustom method.
 */
class TelegramBot_acceptBotCommandCustom_Test
        extends MockitoExtensionBaseMockTests {

    @Spy
    BotCommandCustom botCommandCustom;
    @Spy
    @InjectMocks
    TelegramBot telegramBot;

    @Test
    void acceptBotCommandCustom_Test() {

        // Before
        Update update = new Update();
        Message message = new Message();
        Chat chat = new Chat();
        chat.setId(gen.nextLong());
        message.setChat(chat);
        message.setText("sth");
        update.setMessage(message);

        // When
        Mockito.doReturn((Consumer<Object>) _ -> {})
                .when(botCommandCustom)
                .getConsumer();

        // Action
        telegramBot.acceptBotCommandCustom(botCommandCustom, update);

        // Verify
        Mockito.verify(botCommandCustom, Mockito.times(1)).getConsumer();

    }

}