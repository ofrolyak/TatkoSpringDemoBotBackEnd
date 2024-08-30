package com.tatko.telegram.bot.service.processor;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.service.custom.command.BotCommandCustom;
import com.tatko.telegram.bot.service.custom.command.BotCommandCustomSettingsAction;
import com.tatko.telegram.bot.service.custom.command.BotCommandCustomStartAction;
import com.tatko.telegram.bot.service.custom.storage.BotCommandCustomSetStorage;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class TextMessageProcessorService4parseBotCommandCustom4Test extends MockitoExtensionBaseMockTests {

    @Mock
    BotCommandCustomSetStorage botCommandCustomSetStorage;
    @InjectMocks
    TextMessageProcessorService textMessageProcessorService;

    @Test
    void givenEmptySetCase4thenEmptyResult4Test() {

        // Before
        BotCommandCustomStartAction botCommandCustomStartAction
                = new BotCommandCustomStartAction();

        // When
        when(botCommandCustomSetStorage.getBotCommandCustomSet())
                .thenReturn(Set.of());

        // Action
        Optional<BotCommandCustom> botCommandCustom
                = textMessageProcessorService.parseBotCommandCustom(
                        botCommandCustomStartAction.getMessageText());

        // Then
        assertThat(botCommandCustom.isPresent())
                .isFalse();
    }

    @Test
    void givenNotEmptySetCase4thenNoValue4Test() {

        // Before
        BotCommandCustomStartAction botCommandCustomStartAction
                = new BotCommandCustomStartAction();

        // When
        when(botCommandCustomSetStorage.getBotCommandCustomSet())
                .thenReturn(Set.of(new BotCommandCustomSettingsAction()));

        // Action
        Optional<BotCommandCustom> botCommandCustom
                = textMessageProcessorService.parseBotCommandCustom(
                botCommandCustomStartAction.getMessageText());

        // Then
        assertThat(botCommandCustom.isPresent())
                .isFalse();
    }

    @Test
    void givenNotEmptySetCase4thenYesValue4Test() {

        // Before
        BotCommandCustomStartAction botCommandCustomStartAction
                = new BotCommandCustomStartAction();

        // When
        when(botCommandCustomSetStorage.getBotCommandCustomSet())
                .thenReturn(Set.of(botCommandCustomStartAction));

        // Action
        Optional<BotCommandCustom> botCommandCustom
                = textMessageProcessorService.parseBotCommandCustom(
                        botCommandCustomStartAction.getMessageText());

        // Then
        assertThat(botCommandCustom.isPresent())
                .isTrue();
        assertThat(botCommandCustom.get())
                .isEqualTo(botCommandCustomStartAction);


    }

}