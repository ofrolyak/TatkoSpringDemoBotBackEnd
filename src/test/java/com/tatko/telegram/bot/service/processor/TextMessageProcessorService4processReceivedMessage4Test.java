package com.tatko.telegram.bot.service.processor;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.service.custom.command.BotCommandCustom;
import com.tatko.telegram.bot.service.custom.command.BotCommandCustomStartAction;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class TextMessageProcessorService4processReceivedMessage4Test extends MockitoExtensionBaseMockTests {

    @Mock
    SendMessageOperation2Params sendMessageOperation2Params;
    @Spy
    @InjectMocks
    TextMessageProcessorService textMessageProcessorService;

    @Test
    void givenNotSpecifiedTextMessage4thenJustMessage4Test() {

        // Before
        Update update = getGen().nextUpdate();

        // When
        doReturn(Optional.empty())
                .when(textMessageProcessorService)
                .parseBotCommandCustom(anyString());
        doNothing()
                .when(sendMessageOperation2Params)
                .execute(anyLong(), anyString());

        // Action
        textMessageProcessorService.processReceivedMessage(
                update, sendMessageOperation2Params);

        // Then
        verify(textMessageProcessorService, times(0))
                .acceptBotCommandCustom(any(BotCommandCustom.class), eq(update));
        verify(sendMessageOperation2Params, times(1))
                .execute(anyLong(), anyString());

    }

    @Test
    void givenSpecifiedTextMessage4thenProcessMessage4Test() {

        // Before
        Update update = getGen().nextUpdate();

        // When
        doReturn(Optional.of(new BotCommandCustomStartAction()))
                .when(textMessageProcessorService)
                .parseBotCommandCustom(anyString());
        doNothing()
                .when(textMessageProcessorService)
                .acceptBotCommandCustom(any(BotCommandCustom.class), eq(update));

        // Action
        textMessageProcessorService.processReceivedMessage(
                update, sendMessageOperation2Params);

        // Then
        verify(textMessageProcessorService, times(1))
                .acceptBotCommandCustom(any(BotCommandCustom.class), eq(update));
        verify(sendMessageOperation2Params, times(0))
                .execute(anyLong(), anyString());

    }

}