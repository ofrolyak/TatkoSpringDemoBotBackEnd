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
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
/**
 * JUnit class for TelegramBot class processHelpAction method.
 */
class TelegramBot4processHelpAction4Test
        extends MockitoExtensionBaseMockTests {

    /**
     * TelegramBot instance with injected mocks.
     */
    @Spy
    @InjectMocks
    private TelegramBot telegramBot;


    @Test
    void processHelpAction4Test() {

        // Before
        final Update update = new Update();
        final Message message = new Message();
        final Chat chat = new Chat();
        chat.setId(getGen().nextLong());
        message.setChat(chat);
        final BotCommandCustom botCommandCustom
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
