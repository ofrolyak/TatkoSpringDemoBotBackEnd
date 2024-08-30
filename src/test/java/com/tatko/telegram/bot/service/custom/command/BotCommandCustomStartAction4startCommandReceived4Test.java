package com.tatko.telegram.bot.service.custom.command;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.service.TelegramBotService;
import com.tatko.telegram.bot.service.custom.operation.SendMessageOperation2Params;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class BotCommandCustomStartAction4startCommandReceived4Test extends MockitoExtensionBaseMockTests {

    @Mock
    TelegramBotService telegramBotService;
    @Spy
    @InjectMocks
    BotCommandCustomStartAction botCommandCustomStartAction;

    @SneakyThrows
    @Test
    void success4test() {

        // Before
        final SendMessageOperation2Params sendMessageOperation2Params = (a, b) -> telegramBotService.sendMessage(a, b);
        final long chatId = 1L;
        final String name = "name";

        // When
        doNothing()
                .when(telegramBotService)
                .sendMessage(anyLong(), anyString());

        //Action
        botCommandCustomStartAction.startCommandReceived(sendMessageOperation2Params, chatId, name);

        // After
        verify(telegramBotService, times(1))
                .sendMessage(anyLong(), anyString());


    }

}