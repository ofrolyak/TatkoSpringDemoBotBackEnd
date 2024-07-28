package com.tatko.telegram.bot.service;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * JUnit class for TelegramBot class processReceivedMessage method.
 */
class TelegramBot4processReceivedMessage4Test
        extends MockitoExtensionBaseMockTests {

    /**
     * TelegramBot instance with injected mocks.
     */
    @Spy
    @InjectMocks
    private TelegramBot telegramBot;


    @Test
    void processReceivedMessage4notActionMode4Test() {

        // Before
        final Update update = new Update();
        final Message message = new Message();
        final Chat chat = new Chat();
        chat.setId(getGen().nextLong());
        message.setChat(chat);
        message.setText("sth");
        update.setMessage(message);

        // When
        Mockito.doNothing()
                .when(telegramBot)
                .sendMessage(ArgumentMatchers.anyLong(),
                        ArgumentMatchers.anyString());

        // Action
        telegramBot.processReceivedMessage(update);

        // Then
        Mockito.verify(telegramBot, Mockito.never())
                .acceptBotCommandCustom(
                        ArgumentMatchers.any(BotCommandCustom.class),
                        ArgumentMatchers.any(Update.class));
        Mockito.verify(telegramBot, Mockito.times(1))
                .sendMessage(ArgumentMatchers.anyLong(),
                        ArgumentMatchers.anyString());
    }

    @Test
    void processReceivedMessage4ActionMode4Test() {

        // Before
        final Update update = new Update();
        final Message message = new Message();
        final Chat chat = new Chat();
        chat.setId(getGen().nextLong());
        message.setChat(chat);
        final BotCommandCustom botCommandCustom
                = telegramBot.getBotCommandsSet().stream().findAny().get();
        message.setText(botCommandCustom.getMessageText());
        update.setMessage(message);

        // When
        Mockito.doNothing()
                .when(telegramBot)
                .acceptBotCommandCustom(
                        ArgumentMatchers.any(BotCommandCustom.class),
                        ArgumentMatchers.any(Update.class));

        // Action
        telegramBot.processReceivedMessage(update);

        // Then
        Mockito.verify(telegramBot, Mockito.times(1))
                .acceptBotCommandCustom(
                        ArgumentMatchers.any(BotCommandCustom.class),
                        ArgumentMatchers.any(Update.class));
        Mockito.verify(telegramBot, Mockito.never())
                .sendMessage(
                        ArgumentMatchers.anyLong(),
                        ArgumentMatchers.anyString());
    }

}
