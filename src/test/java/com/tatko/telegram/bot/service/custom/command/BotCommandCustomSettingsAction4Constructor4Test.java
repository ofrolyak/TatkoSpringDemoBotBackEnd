package com.tatko.telegram.bot.service.custom.command;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BotCommandCustomSettingsAction4Constructor4Test {

    @Test
    void success4Test() {

        // Action
        BotCommandCustom botCommandCustom = new BotCommandCustomSettingsAction();

        // Then
        assertThat(botCommandCustom.getName())
                .isEqualTo("SETTINGS");
        assertThat(botCommandCustom.getMessageText())
                .isEqualTo("/settings");

    }

}