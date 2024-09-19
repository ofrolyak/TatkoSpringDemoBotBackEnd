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

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class TextMessageProcessorService4processReceivedCommand4Test extends MockitoExtensionBaseMockTests {

    @Mock
    SendMessageOperation2Params sendMessageOperation2Params;
    @Spy
    @InjectMocks
    TextMessageProcessorService textMessageProcessorService;

    @Test
    void process4Test() {

        // Before
        Update update = getGen().nextUpdate();

        // When
        doNothing()
                .when(textMessageProcessorService)
                .onUpdateReceivedDirect(eq(update), eq(sendMessageOperation2Params));
        doNothing()
                .when(textMessageProcessorService)
                .onUpdateReceivedContains(eq(update));

        // Action
        textMessageProcessorService.processReceivedCommand(update, sendMessageOperation2Params);

        // Then
        verify(textMessageProcessorService, times(1))
                .onUpdateReceivedDirect(eq(update), eq(sendMessageOperation2Params));
        verify(textMessageProcessorService, times(1))
                .onUpdateReceivedContains(eq(update));

    }

}