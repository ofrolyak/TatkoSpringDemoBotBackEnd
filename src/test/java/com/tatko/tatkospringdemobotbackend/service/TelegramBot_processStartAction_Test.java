package com.tatko.tatkospringdemobotbackend.service;

import com.tatko.tatkospringdemobotbackend.MockitoExtensionBaseMockTests;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * JUnit class for TelegramBot class processStartAction method.
 */
class TelegramBot_processStartAction_Test
        extends MockitoExtensionBaseMockTests {

    @Spy
    @InjectMocks
    private TelegramBot telegramBot;


    @Test
    void processStartAction4Test() {

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
                .registerUser(ArgumentMatchers.eq(message));
        doNothing()
                .when(telegramBot)
                .startCommandReceived(ArgumentMatchers.eq(chat.getId()),
                        ArgumentMatchers.eq(update.getMessage().getChat().getFirstName()));

        // Then
        Assertions.assertThatCode(() -> telegramBot.processStartAction(update))
                .doesNotThrowAnyException();
        verify(telegramBot, times(1))
                .registerUser(ArgumentMatchers.eq(message));
        verify(telegramBot, times(1))
                .startCommandReceived(ArgumentMatchers.eq(chat.getId()),
                        ArgumentMatchers.eq(update.getMessage().getChat().getFirstName()));

    }

}
