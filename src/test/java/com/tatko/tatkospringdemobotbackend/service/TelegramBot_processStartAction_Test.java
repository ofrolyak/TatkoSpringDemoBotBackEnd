package com.tatko.tatkospringdemobotbackend.service;

import com.tatko.tatkospringdemobotbackend.MockitoExtensionBaseMockTests;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class TelegramBot_processStartAction_Test
        extends MockitoExtensionBaseMockTests {

    @Spy
    @InjectMocks
    TelegramBot telegramBot;


    @Test
    void processStartAction_Test() {

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
        doNothing()
                .when(telegramBot)
                .registerUser(eq(message));
        doNothing()
                .when(telegramBot)
                .startCommandReceived(eq(chat.getId()),
                        eq(update.getMessage().getChat().getFirstName()));

        // Then
        assertThatCode(() -> telegramBot.processStartAction(update))
                .doesNotThrowAnyException();
        verify(telegramBot, times(1))
                .registerUser(eq(message));
        verify(telegramBot, times(1))
                .startCommandReceived(eq(chat.getId()),
                        eq(update.getMessage().getChat().getFirstName()));

    }

}