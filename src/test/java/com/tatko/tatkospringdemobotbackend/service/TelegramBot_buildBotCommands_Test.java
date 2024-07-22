package com.tatko.tatkospringdemobotbackend.service;

import com.tatko.tatkospringdemobotbackend.BaseMockTests;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TelegramBot_buildBotCommands_Test extends BaseMockTests {

    @Spy
    @InjectMocks
    TelegramBot telegramBot;

    @Test
    void buildBotCommands_verifySize_Test() {

        // Action
        List<BotCommand> botCommands = telegramBot.buildBotCommands();

        // Then
        assertThat(botCommands)
                .hasSize(5);
    }

    @Test
    void buildBotCommands_verifyContent_Test() {

        // Action
        List<BotCommand> botCommands = telegramBot.buildBotCommands();
        List<String> collect = botCommands.stream()
                .map(BotCommand::getCommand)
                .toList();

        // Then
        assertThat(collect)
                .containsExactlyInAnyOrder("/start", "/getmydata", "/deletemydata", "/help", "/settings");
    }

}