package com.tatko.telegram.bot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.retry.annotation.EnableRetry;
/**
 * Application configuration.
 */

@Configuration
@EnableRetry
@PropertySource("classpath:application.yaml")
public class AppConfig {
}
