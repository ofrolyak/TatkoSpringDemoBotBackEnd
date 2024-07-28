package com.tatko.telegram.bot.service;

import java.util.function.Consumer;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * JUnit class for TelegramBot class acceptBotCommandCustom method.
 */
class TelegramBot4acceptBotCommandCustom4Test
        extends MockitoExtensionBaseMockTests {

    /**
     * Spy for BotCommandCustom.
     */
    @Spy
    private BotCommandCustom botCommandCustom;

    /**
     * TelegramBot instance with injected mocks.
     */
    @Spy
    @InjectMocks
    private TelegramBot telegramBot;

    @Test
    void acceptBotCommandCustom4Test() {

        // Before
        final Update update = new Update();
        final Message message = new Message();
        final Chat chat = new Chat();
        chat.setId(getGen().nextLong());
        message.setChat(chat);
        message.setText("sth");
        update.setMessage(message);
        final Consumer<Update> updateConsumer
                = updateParam -> System.out.println(updateParam.toString());

        // When
        Mockito.doReturn(updateConsumer)
                .when(botCommandCustom)
                .getConsumer();

        // Action
        telegramBot.acceptBotCommandCustom(botCommandCustom, update);

        // Verify
        Mockito.verify(botCommandCustom, Mockito.times(1)).getConsumer();

    }

}
