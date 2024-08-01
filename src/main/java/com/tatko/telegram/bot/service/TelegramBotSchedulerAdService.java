package com.tatko.telegram.bot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Telegram Bot service.
 */
@Service
@Slf4j
public class TelegramBotSchedulerAdService {

    @Autowired
    TelegramBotService telegramBotService;
    @Autowired
    AdService adService;

    @Scheduled(cron = "${telegram.bot.scheduler.cron}")
    public void sendNextAd() {

        log.info("Sending ads to all users");

        adService.sendNextAd(telegramBotService.getSendMessageOperation());

        log.info("Ads sent");

    }



}
