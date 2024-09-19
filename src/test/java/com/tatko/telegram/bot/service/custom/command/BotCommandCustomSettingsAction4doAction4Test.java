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

class BotCommandCustomSettingsAction4doAction4Test extends MockitoExtensionBaseMockTests {

    @Spy
    @InjectMocks
    BotCommandCustomSettingsAction botCommandCustomSettingsAction;

    @Test
    void success4Test() {

        // Before
        Update update = getGen().nextUpdate();

        // When
        Mockito.doNothing()
                .when(botCommandCustomSettingsAction)
                .addKeyboardAndSendMessage(eq(update));

        // Action
        botCommandCustomSettingsAction.doAction(update);

        // Then
        verify(botCommandCustomSettingsAction, times(1))
                .addKeyboardAndSendMessage(any(Update.class));

    }

}