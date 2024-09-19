package com.tatko.telegram.bot.service.custom.command;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.service.TelegramBotConfiguratorService;
import com.tatko.telegram.bot.service.TelegramBotService;
import com.tatko.telegram.bot.service.custom.operation.SendMessageOperation3Params;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class BotCommandCustom4addKeyboardAndSendMessage4Test extends MockitoExtensionBaseMockTests {

    @Mock
    TelegramBotConfiguratorService telegramBotConfiguratorService;
    @Mock
    TelegramBotService telegramBotService;
    @Spy
    @InjectMocks
    BotCommandCustomServiceAction botCommandCustom;

    @Test
    void success4Test() {

        // Before
        Update update = getGen().nextUpdate();
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        // When
        doReturn(replyKeyboardMarkup)
                .when(botCommandCustom)
                        .createReplyKeyboardMarkup();
        doReturn((SendMessageOperation3Params) (a, b, c) -> telegramBotService.sendMessage(a,b,c))
                .when(telegramBotConfiguratorService)
                .getOperationByClass(eq(SendMessageOperation3Params.class));
        doNothing()
                .when(telegramBotService)
                .sendMessage(nullable(Long.class), nullable(String.class), any(ReplyKeyboardMarkup.class));

        // Action
        botCommandCustom.addKeyboardAndSendMessage(update);

        // Then
        verify(telegramBotService, times(1))
                .sendMessage(nullable(Long.class), nullable(String.class), any(ReplyKeyboardMarkup.class));

    }

}