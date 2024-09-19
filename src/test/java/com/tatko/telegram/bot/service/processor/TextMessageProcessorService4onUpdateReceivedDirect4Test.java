package com.tatko.telegram.bot.service.processor;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.service.custom.operation.SendMessageOperation2Params;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


class TextMessageProcessorService4onUpdateReceivedDirect4Test extends MockitoExtensionBaseMockTests {

    @Mock
    SendMessageOperation2Params sendMessageOperation2Params;
    @Spy
    @InjectMocks
    TextMessageProcessorService textMessageProcessorService;

    @Test
    void givenSpecific4Test() {

        // Before
        Update update = getGen().nextUpdate();

        // When
        doReturn(Optional.empty())
                .when(textMessageProcessorService)
                .parseBotCommandCustom(anyString());

        // Action
        textMessageProcessorService.onUpdateReceivedDirect(
                update, sendMessageOperation2Params);

        // Then
        verify(textMessageProcessorService, times(0))
                .processReceivedMessage(any(Update.class),
                        any(SendMessageOperation2Params.class));

    }

    @Test
    void givenNotSpecific4Test() {

        // Before
        Update update = getGen().nextUpdate();

        // When
        doReturn(Optional.of(sendMessageOperation2Params))
                .when(textMessageProcessorService)
                .parseBotCommandCustom(anyString());
        doNothing()
                .when(textMessageProcessorService)
                .processReceivedMessage(any(Update.class),
                        any(SendMessageOperation2Params.class));

        // Action
        textMessageProcessorService.onUpdateReceivedDirect(
                update, sendMessageOperation2Params);

        // Then
        verify(textMessageProcessorService, times(1))
                .processReceivedMessage(any(Update.class),
                        any(SendMessageOperation2Params.class));

    }

}