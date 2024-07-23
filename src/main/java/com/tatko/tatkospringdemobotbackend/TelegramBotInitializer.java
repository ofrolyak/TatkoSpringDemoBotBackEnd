package com.tatko.tatkospringdemobotbackend;

import com.tatko.tatkospringdemobotbackend.service.TelegramBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@Component
@Profile("prod")
public class TelegramBotInitializer {

    @Autowired
    TelegramBot telegramBot;

    @JUnitTestCodeCoverageSkipGenerated
    public void createRegisterBot() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(telegramBot);
    }

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {

        log.info("Initializing TelegramBot");
        try {
            createRegisterBot();
            telegramBot.addCommandToBot();
        } catch (TelegramApiException e) {
            log.error("Initializing TelegramBot Error: ", e);
            throw new TelegramApiException(e);
        }
        log.info("TelegramBot initialized");
    }

}
