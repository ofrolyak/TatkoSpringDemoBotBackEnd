package com.tatko.telegram.bot.service.custom.command;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.service.TelegramBotConfiguratorService;
import com.tatko.telegram.bot.service.TelegramBotService;
import com.tatko.telegram.bot.service.custom.operation.SendMessageOperation2Params;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class BotCommandCustomHelpAction4doAction4Test extends MockitoExtensionBaseMockTests {

    @Mock
    TelegramBotConfiguratorService telegramBotConfiguratorService;
    @Mock
    TelegramBotService telegramBotService;
    @Spy
    @InjectMocks
    BotCommandCustomHelpAction botCommandCustomHelpAction;

    @Test
    void success4Test() {

        // Before
        Update update = getGen().nextUpdate();

        // When
        doReturn((SendMessageOperation2Params) (a, b) -> telegramBotService.sendMessage(a,b))
                .when(telegramBotConfiguratorService)
                .getOperationByClass(eq(SendMessageOperation2Params.class));
        doNothing()
                .when(telegramBotService)
                .sendMessage(nullable(Long.class), nullable(String.class));

        // Action
        botCommandCustomHelpAction.doAction(update);

        // Then
        verify(telegramBotService, times(1))
                .sendMessage(nullable(Long.class), nullable(String.class));


    }

}