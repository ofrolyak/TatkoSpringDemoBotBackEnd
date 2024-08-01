package com.tatko.telegram.bot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Telegram Bot service.
 */
@Component
@Slf4j
public class TelegramBotSchedulerDataFactService {

    @Autowired
    TelegramBotService telegramBotService;
    @Autowired
    DateFactService dateFactService;

    @Scheduled(cron = "${telegram.bot.scheduler.cron}")
    public void send() {

        log.info("Sending ads to all users");

        dateFactService.sendNextDateFact(telegramBotService.getSendMessageOperation());

        log.info("Ads sent");

    }



}
