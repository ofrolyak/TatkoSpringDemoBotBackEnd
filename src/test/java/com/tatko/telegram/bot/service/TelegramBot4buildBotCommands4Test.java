package com.tatko.telegram.bot.service;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;
/**
 * JUnit class for TelegramBot class buildBotCommands method.
 */

class TelegramBot4buildBotCommands4Test extends MockitoExtensionBaseMockTests {

    /**
     * TelegramBot instance with injected mocks.
     */
    @Spy
    @InjectMocks
    private TelegramBot telegramBot;


    @Test
    void buildBotCommands4verifySize4Test() {

        // Action
        final List<BotCommand> botCommands = telegramBot.buildBotCommands();

        // Then
        Assertions.assertThat(botCommands)
                .hasSize(7);
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
                        "/deletemydata", "/help", "/settings", "/register", "/send");
    }

}
