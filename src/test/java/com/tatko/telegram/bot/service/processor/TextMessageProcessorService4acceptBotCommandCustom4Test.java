package com.tatko.telegram.bot.service.processor;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.service.custom.command.BotCommandCustomStartAction;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class TextMessageProcessorService4acceptBotCommandCustom4Test extends MockitoExtensionBaseMockTests {

    @Mock
    BotCommandCustomStartAction botCommandCustomStartAction;
    @InjectMocks
    TextMessageProcessorService textMessageProcessorService;

    @Test
    void acceptBotCommandCustom() {

        // Before
        Update update = getGen().nextUpdate();

        // When
        doNothing()
                .when(botCommandCustomStartAction)
                .doAction(eq(update));

        // Action
        textMessageProcessorService.acceptBotCommandCustom(botCommandCustomStartAction, update);

        // Then
        verify(botCommandCustomStartAction, times(1))
                .doAction(eq(update));


    }
}