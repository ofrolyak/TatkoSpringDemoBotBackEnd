package com.tatko.telegram.bot.service.processor;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.service.TelegramBotConfiguratorService;
import com.tatko.telegram.bot.service.TelegramBotService;
import com.tatko.telegram.bot.service.custom.operation.SendMessageOperation2Params;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class TelegramBotProcessorService4onUpdateReceived4Test extends MockitoExtensionBaseMockTests {

    @Mock
    CallbackProcessorService callbackProcessorService;
    @Mock
    TelegramBotService telegramBotService;
    @Mock
    TextMessageProcessorService textMessageProcessorService;
    @Mock
    TelegramBotConfiguratorService telegramBotConfiguratorService;
    @InjectMocks
    TelegramBotProcessorService telegramBotProcessorService;

    @Test
    void textMessageCase4Test() {

        // Before
        Update update = getGen().nextUpdate();

        // When
        doNothing()
                .when(textMessageProcessorService)
                .processReceivedTextMessage(
                        eq(update), any(SendMessageOperation2Params.class));
        doReturn((SendMessageOperation2Params) (a, b) -> telegramBotService.sendMessage(a,b))
                .when(telegramBotConfiguratorService)
                .getOperationByClass(eq(SendMessageOperation2Params.class));

        // Action
        telegramBotProcessorService.onUpdateReceived(update);

        // Then
        verify(textMessageProcessorService, times(1))
                .processReceivedTextMessage(
                        eq(update), any(SendMessageOperation2Params.class));
        verify(callbackProcessorService, never())
                .processReceivedCallback(eq(update));
    }

    @Test
    void callbackMessageCase4Test() {

        // Before
        Update update = getGen().nextUpdate();
        update.setMessage(null);
        update.setCallbackQuery(new CallbackQuery());

        // When
        doNothing()
                .when(callbackProcessorService)
                .processReceivedCallback(eq(update));

        // Action
        telegramBotProcessorService.onUpdateReceived(update);

        // Then
        verify(textMessageProcessorService, never())
                .processReceivedTextMessage(
                        eq(update), any(SendMessageOperation2Params.class));
        verify(callbackProcessorService, times(1))
                .processReceivedCallback(eq(update));
    }

    @Test
    void failure1Case4Test() {

        // Before
        Update update = getGen().nextUpdate();
        update.setMessage(null);

        // Action
        assertThatCode(() -> telegramBotProcessorService.onUpdateReceived(update))
                .isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    void failure2Case4Test() {

        // Before
        Update update = getGen().nextUpdate();
        update.setMessage(null);

        // Action
        assertThatCode(() -> telegramBotProcessorService.onUpdateReceived(update))
                .isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    void failure3Case4Test() {

        // Before
        Update update = getGen().nextUpdate();
        update.setMessage(null);
        update.setCallbackQuery(null);

        // Action
        assertThatCode(() -> telegramBotProcessorService.onUpdateReceived(update))
                .isInstanceOf(IllegalArgumentException.class);

    }


}