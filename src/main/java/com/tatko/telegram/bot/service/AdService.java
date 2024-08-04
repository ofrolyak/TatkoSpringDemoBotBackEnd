package com.tatko.telegram.bot.service;

import com.tatko.telegram.bot.dao.AdDao;
import com.tatko.telegram.bot.entity.Ad;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class AdService {

    @Autowired
    UserService userService;

    @Autowired
    AdDao adDao;

    private void refreshDeliveredDateForAd(@NotNull final Ad ad) {

        log.debug("Refreshing delivered dates for ad {}", ad);

        ad.setDeliveredTime(LocalDateTime.now());
        adDao.save(ad);

        log.debug("Ad {} has been refreshed", ad);
    }

    void deliverAdToUsers(Operation sendMessageOperation, @NotNull final Ad ad) {

        log.debug("Delivering ad {} to users", ad);

        userService.deliverToUsers(sendMessageOperation, ad.getAd());

        refreshDeliveredDateForAd(ad);

        log.debug("Ad {} has been delivered", ad);
    }

    @Value("${telegram.bot.scheduler.ad.frequency.hour}")
    int telegramBotSchedulerDdFrequencyHour;

    public void sendNextAd(Operation sendMessageOperation) {

        log.info("Sending ads to all users");

        var adOptional = adDao.findAdToDeliver(LocalDateTime.now().minusHours(telegramBotSchedulerDdFrequencyHour));

        adOptional.ifPresentOrElse(ad -> deliverAdToUsers(sendMessageOperation, ad), () -> log.info("There are not adOptional to deliver"));

        log.info("Ads sent");

    }

}
