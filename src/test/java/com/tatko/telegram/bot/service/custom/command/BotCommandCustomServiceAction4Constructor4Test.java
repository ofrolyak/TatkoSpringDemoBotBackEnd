package com.tatko.telegram.bot.service.custom.command;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BotCommandCustomServiceAction4Constructor4Test {

    @Test
    void success4Test() {

        // Action
        BotCommandCustom botCommandCustom = new BotCommandCustomServiceAction();

        // Then
        assertThat(botCommandCustom.getName())
                .isEqualTo("SERVICE");
        assertThat(botCommandCustom.getMessageText())
                .isEqualTo("/service");

    }

}