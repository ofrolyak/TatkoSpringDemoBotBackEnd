package com.tatko.telegram.bot.service.custom.storage;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.service.TelegramBotService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ExecutorMapStorage4getSendMessageOperation3ParamsOperation4Test
        extends MockitoExtensionBaseMockTests {

    @Mock
    private TelegramBotService telegramBotService;
    @InjectMocks
    private ExecutorMapStorage executorMapStorage;

    @Test
    void success4Test() {

        // Before
        long chatId = getGen().nextLong();
        String textMessage = getGen().nextString();
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();


        // When
        doNothing()
                .when(telegramBotService)
                .sendMessage(eq(chatId), eq(textMessage), eq(replyKeyboardMarkup));

        // Action
        executorMapStorage.getSendMessageSendMessage3ParamsOperation().execute(chatId, textMessage, replyKeyboardMarkup);

        // Then
        verify(telegramBotService, times(1))
                .sendMessage(anyLong(), anyString(), any(ReplyKeyboardMarkup.class));

    }

}