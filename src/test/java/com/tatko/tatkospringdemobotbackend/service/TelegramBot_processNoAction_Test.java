package com.tatko.tatkospringdemobotbackend.service;

import com.tatko.tatkospringdemobotbackend.MockitoExtensionBaseMockTests;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * JUnit class for TelegramBot class processNoAction method.
 */
class TelegramBot_processNoAction_Test
        extends MockitoExtensionBaseMockTests {

    /**
     * TelegramBot instance with injected mocks.
     */
    @Spy
    @InjectMocks
    private TelegramBot telegramBot;


    @Test
    void processNoAction4Test() {

        // Before
        final Update update = new Update();
        final Message message = new Message();
        final Chat chat = new Chat();
        chat.setId(gen.nextLong());
        message.setChat(chat);
        final BotCommandCustom botCommandCustom
                = telegramBot.getBotCommandsSet().stream().findAny().get();
        message.setText(botCommandCustom.getMessageText());
        update.setMessage(message);

        // Then
        Assertions.assertThatCode(() -> telegramBot.processNoAction(update))
                .doesNotThrowAnyException();

    }

}
