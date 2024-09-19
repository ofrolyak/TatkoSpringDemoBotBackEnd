package com.tatko.telegram.bot.service.custom.command;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class BotCommandCustomServiceAction4doAction4Test extends MockitoExtensionBaseMockTests {

    @Spy
    @InjectMocks
    BotCommandCustomServiceAction botCommandCustomServiceAction;

    @Test
    void success4Test() {

        // Before
        Update update = getGen().nextUpdate();

        // When
        Mockito.doNothing()
                .when(botCommandCustomServiceAction)
                .addKeyboardAndSendMessage(eq(update));

        // Action
        botCommandCustomServiceAction.doAction(update);

        // Then
        verify(botCommandCustomServiceAction, times(1))
                .addKeyboardAndSendMessage(any(Update.class));

    }

}