package com.tatko.tatkospringdemobotbackend.service;

import com.tatko.tatkospringdemobotbackend.MockitoExtensionBaseMockTests;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

/**
 * JUnit class for TelegramBot class buildBotCommands method.
 */
class TelegramBot_buildBotCommands_Test extends MockitoExtensionBaseMockTests {

    @Spy
    @InjectMocks
    TelegramBot telegramBot;


    @Test
    void buildBotCommands4verifySize4Test() {

        // Action
        List<BotCommand> botCommands = telegramBot.buildBotCommands();

        // Then
        Assertions.assertThat(botCommands)
                .hasSize(5);
    }

    @Test
    void buildBotCommands4verifyContent4Test() {

        // Action
        List<BotCommand> botCommands = telegramBot.buildBotCommands();
        List<String> collect = botCommands.stream()
                .map(BotCommand::getCommand)
                .toList();

        // Then
        Assertions.assertThat(collect)
                .containsExactlyInAnyOrder("/start", "/getmydata",
                        "/deletemydata", "/help", "/settings");
    }

}
