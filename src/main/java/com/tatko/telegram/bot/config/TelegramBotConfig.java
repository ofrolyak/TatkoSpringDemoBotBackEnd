package com.tatko.telegram.bot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Configuration for Telegram Bot.
 */

@Configuration
@PropertySource("application.yaml")
@Data
@EnableScheduling
public class TelegramBotConfig {

    /**
     * Telegram Bot Name.
     */
    @Value("${telegram.bot.name}")
    private String telegramBotName;

    /**
     * Telegram Bot Token.
     */
    @Value("${telegram.bot.token}")
    private String telegramBotToken;

    /**
     * Telegram Bot Owner.
     */
    @Value("${telegram.bot.owner}")
    private long telegramBotOwner;

}
