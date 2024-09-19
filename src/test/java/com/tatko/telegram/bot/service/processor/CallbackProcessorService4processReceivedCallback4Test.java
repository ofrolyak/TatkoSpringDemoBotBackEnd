package com.tatko.telegram.bot.service.processor;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.service.TelegramBotConfiguratorService;
import com.tatko.telegram.bot.service.business.DateFactService;
import com.tatko.telegram.bot.service.custom.operation.SendMessageOperation1Param;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.tatko.telegram.bot.service.custom.button.InlineKeyButton.GET_NEXT_DATE_FACT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class CallbackProcessorService4processReceivedCallback4Test extends MockitoExtensionBaseMockTests {

    @Mock
    TelegramBotConfiguratorService telegramBotConfiguratorService;
    @Mock
    DateFactService dateFactService;
    @InjectMocks
    CallbackProcessorService callbackProcessorService;

    @Test
    void processReceivedCallback4Test() {

        // Before
        Update update = getGen().nextUpdate();
        Message message = getGen().nextMessage();
        CallbackQuery callbackQuery = new CallbackQuery();
        callbackQuery.setMessage(message);
        callbackQuery.setData("fake");
        update.setCallbackQuery(callbackQuery);

        // Action
        callbackProcessorService.processReceivedCallback(update);

        // Then
        verify(dateFactService, never())
                .sendNextDateFact(any(SendMessageOperation1Param.class), anyLong());

    }

    @Test
    void processReceivedCallback4GetNextDateFactCase4Test() {

        // Before
        Update update = getGen().nextUpdate();
        Message message = getGen().nextMessage();
        CallbackQuery callbackQuery = new CallbackQuery();
        callbackQuery.setMessage(message);
        callbackQuery.setData(GET_NEXT_DATE_FACT.getLabel());
        update.setCallbackQuery(callbackQuery);

        // When
        doNothing()
                .when(dateFactService)
                .sendNextDateFact(any(SendMessageOperation1Param.class), anyLong());
        doReturn(mock(SendMessageOperation1Param.class))
                .when(telegramBotConfiguratorService)
                .getOperationByClass(eq(SendMessageOperation1Param.class));

        // Action
        callbackProcessorService.processReceivedCallback(update);

        // Then
        verify(dateFactService, times(1))
                .sendNextDateFact(any(SendMessageOperation1Param.class), anyLong());

    }

}