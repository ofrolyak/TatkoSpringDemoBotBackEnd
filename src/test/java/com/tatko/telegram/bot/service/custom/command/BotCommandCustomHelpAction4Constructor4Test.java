package com.tatko.telegram.bot.service.custom.command;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BotCommandCustomHelpAction4Constructor4Test {

    @Test
    void success4Test() {

        // Action
        BotCommandCustom botCommandCustom = new BotCommandCustomHelpAction();

        // Then
        assertThat(botCommandCustom.getName())
                .isEqualTo("HELP");
        assertThat(botCommandCustom.getMessageText())
                .isEqualTo("/help");

    }

}