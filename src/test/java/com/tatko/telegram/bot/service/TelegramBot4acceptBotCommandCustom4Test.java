package com.tatko.telegram.bot.service;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.service.custom.command.BotCommandCustom;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
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
        Mockito.doNothing()
                .when(botCommandCustom)
                .doAction(ArgumentMatchers.eq(telegramBot), ArgumentMatchers.eq(update));

        // Action
        telegramBot.acceptBotCommandCustom(botCommandCustom, update);

        // Verify
        Mockito.verify(botCommandCustom, Mockito.times(1))
                .doAction(ArgumentMatchers.eq(telegramBot), ArgumentMatchers.eq(update));

    }

}
