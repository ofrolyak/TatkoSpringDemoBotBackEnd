package com.tatko.tatkospringdemobotbackend.service;

import com.tatko.tatkospringdemobotbackend.MockitoExtensionBaseMockTests;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.assertj.core.api.Assertions.assertThatCode;

class TelegramBot_processNoAction_Test extends MockitoExtensionBaseMockTests {

    @Spy
    @InjectMocks
    TelegramBot telegramBot;


    @Test
    void processNoAction_Test() {

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

        // Then
        assertThatCode(() -> telegramBot.processNoAction(update))
                .doesNotThrowAnyException();

    }

}