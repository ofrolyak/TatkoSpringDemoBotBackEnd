package com.tatko.tatkospringdemobotbackend.service;

import com.tatko.tatkospringdemobotbackend.MockitoExtensionBaseMockTests;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

/**
 * JUnit class for TelegramBot class processHelpAction method.
 */
class TelegramBot_processHelpAction_Test
        extends MockitoExtensionBaseMockTests {

    @Spy
    @InjectMocks
    TelegramBot telegramBot;


    @Test
    void processHelpAction_Test() {

        // Before
        Update update = new Update();
        Message message = new Message();
        Chat chat = new Chat();
        chat.setId(gen.nextLong());
        message.setChat(chat);
        BotCommandCustom botCommandCustom
                = telegramBot.getBotCommandsSet().stream()
                .findAny().get();
        message.setText(botCommandCustom.getMessageText());
        update.setMessage(message);

        // When
        Mockito.doNothing()
                .when(telegramBot)
                .sendMessage(ArgumentMatchers.eq(chat.getId()),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.any(ReplyKeyboardMarkup.class));

        // Then
        Assertions.assertThatCode(() -> telegramBot.processHelpAction(update))
                .doesNotThrowAnyException();
        Mockito.verify(telegramBot, Mockito.times(1))
                .sendMessage(ArgumentMatchers.eq(chat.getId()),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.any(ReplyKeyboardMarkup.class));

    }

}