package com.tatko.telegram.bot.service;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
/**
 * JUnit class for TelegramBot class processStartAction method.
 */

class TelegramBot4processStartAction4Test
        extends MockitoExtensionBaseMockTests {

    /**
     * TelegramBot instance with injected mocks.
     */
    @Spy
    @InjectMocks
    private TelegramBot telegramBot;


    @Test
    void processStartAction4Test() {

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
                .registerUser(ArgumentMatchers.eq(message));
        Mockito.doNothing()
                .when(telegramBot)
                .startCommandReceived(
                        ArgumentMatchers.eq(chat.getId()),
                        ArgumentMatchers.eq(update.getMessage()
                                .getChat().getFirstName()));

        // Then
        Assertions.assertThatCode(() -> telegramBot.processStartAction(update))
                .doesNotThrowAnyException();
        Mockito.verify(telegramBot, Mockito.times(1))
                .registerUser(ArgumentMatchers.eq(message));
        Mockito.verify(telegramBot, Mockito.times(1))
                .startCommandReceived(ArgumentMatchers.eq(chat.getId()),
                        ArgumentMatchers.eq(update.getMessage().getChat().getFirstName()));

    }

}
