package com.tatko.telegram.bot.service.schedule;

import com.tatko.telegram.bot.service.TelegramBotConfiguratorService;
import com.tatko.telegram.bot.service.business.AdService;
import com.tatko.telegram.bot.service.custom.operation.SendMessageOperation2Params;
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

    /**
     * Autowired by Spring AdService bean.
     */
    @Autowired
    private AdService adService;

    /**
     * Autowired by Spring TelegramBotConfiguratorService bean.
     */
    @Autowired
    private TelegramBotConfiguratorService telegramBotConfiguratorService;

    /**
     * Send next Ad for all users.
     */
    @Scheduled(cron = "${telegram.bot.scheduler.cron}")
    public void sendNextAd() {

        log.info("Sending ads to all users");

        adService.sendNextAd(telegramBotConfiguratorService.getOperationByClass(
                SendMessageOperation2Params.class));

        log.info("Ads sent");

    }


}
