package com.tatko.telegram.bot;

import com.tatko.telegram.bot.service.TelegramBotConfiguratorService;
import com.tatko.telegram.bot.service.TelegramBotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * TelegramBotInitializer class is provide functionality to create and
 * configure Telegram Bot.
 */

@Slf4j
@Component
@Profile("prod")
@JUnitTestCodeCoverageSkipGenerated
public class TelegramBotInitializer {

    /**
     * TelegramBot itself.
     */
    @Autowired
    private TelegramBotService telegramBotService;

    /**
     * Autowired by Spring TelegramBotConfiguratorService bean.
     */
    @Autowired
    private TelegramBotConfiguratorService telegramBotConfiguratorService;

    /**
     * Create registered Telegram bot.
     *
     * @throws TelegramApiException Possible checked TelegramApiException
     */
    public void createRegisterBot() throws TelegramApiException {
        log.info("Registering Telegram Bot to Telegram Bot");
        final TelegramBotsApi telegramBotsApi
                = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(telegramBotService);
        log.info("Registered Telegram Bot to Telegram Bot");
    }

    /**
     * Init registered Telegram bot.
     */
    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {

        log.info("Initializing TelegramBot");
        try {
            createRegisterBot();
            //telegramBotService.addPreparedBotCommandsToBot();
            telegramBotConfiguratorService.addPreparedBotCommandsToBot();
        } catch (TelegramApiException e) {
            log.error("Initializing TelegramBot Error: ", e);
            throw new TelegramApiException(e);
        }
        log.info("TelegramBot initialized");
    }

}
