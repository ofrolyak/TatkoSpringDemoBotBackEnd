package com.tatko.tatkospringdemobotbackend.service;

import java.util.List;

import com.tatko.tatkospringdemobotbackend.MockitoExtensionBaseMockTests;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

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
        final List<BotCommand> botCommands = telegramBot.buildBotCommands();

        // Then
        Assertions.assertThat(botCommands)
                .hasSize(5);
    }

    @Test
    void buildBotCommands4verifyContent4Test() {

        // Action
        final List<BotCommand> botCommands = telegramBot.buildBotCommands();
        final List<String> collect = botCommands.stream()
                .map(BotCommand::getCommand)
                .toList();

        // Then
        Assertions.assertThat(collect)
                .containsExactlyInAnyOrder("/start", "/getmydata",
                        "/deletemydata", "/help", "/settings");
    }

}
