package com.tatko.tatkospringdemobotbackend.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("application.yaml")
@Data
public class TelegramBotConfig {

    @Value("${telegram.bot.name}")
    String telegramBotName;

    @Value("${telegram.bot.token}")
    String telegramBotToken;

}
