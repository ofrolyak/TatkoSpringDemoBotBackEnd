package com.tatko.telegram.bot.service.business;

import com.tatko.telegram.bot.dao.AdDao;
import com.tatko.telegram.bot.entity.Ad;
import com.tatko.telegram.bot.service.internal.UserService;
import com.tatko.telegram.bot.service.custom.operation.SendMessageOperation2Params;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class AdService {

    /**
     * Autowired by Spring UserService bean.
     */
    @Autowired
    private UserService userService;

    /**
     * Autowired by Spring AdDao bean.
     */
    @Autowired
    private AdDao adDao;

    private void refreshDeliveredDateForAd(@NotNull final Ad ad) {

        log.debug("Refreshing delivered dates for ad {}", ad);

        ad.setDeliveredTime(LocalDateTime.now());
        adDao.save(ad);

        log.debug("Ad {} has been refreshed", ad);
    }

    /**
     * Deliver Ad to all users.
     * @param sendMessageOperation2Params
     * @param ad
     */
    void deliverAdToUsers(
            final SendMessageOperation2Params sendMessageOperation2Params,
            @NotNull final Ad ad) {

        log.debug("Delivering ad {} to users", ad);

        userService.deliverToUsers(sendMessageOperation2Params, ad.getAd());

        refreshDeliveredDateForAd(ad);

        log.debug("Ad {} has been delivered", ad);
    }

    /**
     * Frequency parameter for Ad scheduler.
     */
    @Value("${telegram.bot.scheduler.ad.frequency.hour}")
    private int telegramBotSchedulerAdFrequencyHour;

    /**
     * Sent the next Ad to all users.
     * @param sendMessageOperation2Params
     */
    public void sendNextAd(
            final SendMessageOperation2Params sendMessageOperation2Params) {

        log.info("Sending ads to all users");

        var adOptional = adDao.findAdToDeliver(LocalDateTime.now()
                .minusHours(telegramBotSchedulerAdFrequencyHour));

        adOptional.ifPresentOrElse(ad ->
                deliverAdToUsers(sendMessageOperation2Params, ad),
                () -> log.info("There are not adOptional to deliver"));

        log.info("Ads sent");

    }

}
