package com.tatko.telegram.bot.service.schedule;

import com.tatko.telegram.bot.service.TelegramBotConfiguratorService;
import com.tatko.telegram.bot.service.business.DateFactService;
import com.tatko.telegram.bot.service.custom.operation.SendMessageOperation1Param;
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

    /**
     * Autowired by Spring DateFactService bean.
     */
    @Autowired
    private DateFactService dateFactService;

    /**
     * Autowired by Spring TelegramBotConfiguratorService bean.
     */
    @Autowired
    private TelegramBotConfiguratorService telegramBotConfiguratorService;

    /**
     * Send the next Dat fact to all users.
     */
    @Scheduled(cron = "${telegram.bot.scheduler.cron}")
    public void send() {

        log.info("Sending ads to all users");

        dateFactService.sendNextDateFact(
                telegramBotConfiguratorService
                        .getOperationByClass(SendMessageOperation1Param.class));

        log.info("Ads sent");

    }



}
