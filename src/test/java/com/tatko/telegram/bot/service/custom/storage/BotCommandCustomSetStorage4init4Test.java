package com.tatko.telegram.bot.service.custom.storage;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.service.custom.command.BotCommandCustomHelpAction;
import com.tatko.telegram.bot.service.custom.command.BotCommandCustomServiceAction;
import com.tatko.telegram.bot.service.custom.command.BotCommandCustomSettingsAction;
import com.tatko.telegram.bot.service.custom.command.BotCommandCustomStartAction;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class BotCommandCustomSetStorage4init4Test extends MockitoExtensionBaseMockTests {

    @Mock
    private ApplicationContext applicationContext;
    @InjectMocks
    private BotCommandCustomSetStorage botCommandCustomSetStorage;

    @Test
    void before4SetCase4Test() {

        // Then
        assertThat(botCommandCustomSetStorage.getBotCommandCustomSet())
                .isEmpty();

    }

    @Test
    void before4ListCase4Test() {

        // Then
        assertThat(botCommandCustomSetStorage.getBotCommandList())
                .isEmpty();

    }

    @Test
    void postConstruct4successSetCase4Test() {

        // When
        when(applicationContext.getBean(eq(BotCommandCustomStartAction.class)))
                .thenReturn(new BotCommandCustomStartAction());
        when(applicationContext.getBean(eq(BotCommandCustomSettingsAction.class)))
                .thenReturn(new BotCommandCustomSettingsAction());
        when(applicationContext.getBean(eq(BotCommandCustomServiceAction.class)))
                .thenReturn(new BotCommandCustomServiceAction());
        when(applicationContext.getBean(eq(BotCommandCustomHelpAction.class)))
                .thenReturn(new BotCommandCustomHelpAction());

        // Action
        botCommandCustomSetStorage.init();

        // Then
        assertThat(botCommandCustomSetStorage.getBotCommandCustomSet())
                .hasSize(4);

    }

    @Test
    void postConstruct4successListCase4Test() {

        // When
        when(applicationContext.getBean(eq(BotCommandCustomStartAction.class)))
                .thenReturn(new BotCommandCustomStartAction());
        when(applicationContext.getBean(eq(BotCommandCustomSettingsAction.class)))
                .thenReturn(new BotCommandCustomSettingsAction());
        when(applicationContext.getBean(eq(BotCommandCustomServiceAction.class)))
                .thenReturn(new BotCommandCustomServiceAction());
        when(applicationContext.getBean(eq(BotCommandCustomHelpAction.class)))
                .thenReturn(new BotCommandCustomHelpAction());

        // Action
        botCommandCustomSetStorage.init();

        // Then
        assertThat(botCommandCustomSetStorage.getBotCommandList())
                .hasSize(4);

    }

}