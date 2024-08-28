package com.tatko.telegram.bot.service.custom.storage;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.service.TelegramBotService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ExecutorMapStorage4getSendMessageOperation1ParamOperation4Test
        extends MockitoExtensionBaseMockTests {

    @Mock
    private TelegramBotService telegramBotService;
    @InjectMocks
    private ExecutorMapStorage executorMapStorage;

    @Test
    void success4Test() {

        // Before
        SendMessage sendMessage = new SendMessage();


        // When
        doNothing()
                .when(telegramBotService)
                .sendMessage(eq(sendMessage));

        // Action
        executorMapStorage.getSendMessageSendMessage1ParamOperation().execute(sendMessage);

        // Then
        verify(telegramBotService, times(1))
                .sendMessage(any(SendMessage.class));

    }

}