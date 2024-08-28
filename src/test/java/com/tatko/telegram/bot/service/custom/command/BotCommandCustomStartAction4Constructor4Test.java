package com.tatko.telegram.bot.service.custom.command;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BotCommandCustomStartAction4Constructor4Test {

    @Test
    void success4Test() {

        // Action
        BotCommandCustom botCommandCustom = new BotCommandCustomStartAction();

        // Then
        assertThat(botCommandCustom.getName())
                .isEqualTo("START");
        assertThat(botCommandCustom.getMessageText())
                .isEqualTo("/start");

    }

}