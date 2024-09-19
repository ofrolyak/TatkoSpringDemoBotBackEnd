package com.tatko.telegram.bot.service.business;

import com.tatko.telegram.bot.service.custom.operation.SendMessageOperation1Param;
import com.tatko.telegram.bot.service.custom.operation.SendMessageOperation2Params;
import com.tatko.telegram.bot.service.external.NumbersApiService;
import com.tatko.telegram.bot.service.internal.UserService;
import com.tatko.telegram.bot.service.processor.CallbackProcessorService;
import com.tatko.telegram.bot.util.StaticUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
@Slf4j
public class DateFactService {

    /**
     * Autowired by Spring NumbersApiService bean.
     */
    @Autowired
    private NumbersApiService numbersApiService;

    /**
     * Autowired by Spring UserService bean.
     */
    @Autowired
    private UserService userService;

    /**
     * Get Mono instance.
     *
     * @return Mono objects.
     */
    Mono<String> getDateFactForThisDay() {

        log.debug("Process getDateFactForThisDay");
        int day = LocalDate.now().getDayOfMonth();
        int month = LocalDate.now().getMonthValue();
        log.debug("Process getDateFactForThisDay for day {}, month {}",
                day, month);
        Mono<String> dateFactForDay
                = numbersApiService.getDateFactForDay(month, day);
        log.debug("Process finished getDateFactForThisDay dateFactForDay: {}",
                dateFactForDay);
        return dateFactForDay;

    }

    /**
     * Send next date fact to all users.
     * @param sendMessageOperation2Params
     */
    public void sendNextDateFact(
            final SendMessageOperation2Params sendMessageOperation2Params) {

        log.info("Sending date fact to all users");

        Mono<String> stringMono = getDateFactForThisDay();

        stringMono.subscribe(dataFact -> userService
                .deliverToUsers(sendMessageOperation2Params, dataFact));

        log.info("Date fact sent");

    }

    /**
     * Send next date fact to all users.
     * @param sendMessageOperation1Param
     */
    public void sendNextDateFact(
            final SendMessageOperation1Param sendMessageOperation1Param) {

        log.info("Sending date fact to all users");

        Mono<String> stringMono = getDateFactForThisDay();

        stringMono.subscribe(dataFact ->
            userService.deliverToUsers(sendMessageOperation1Param, dataFact));

        log.info("Date fact sent");

    }

    /**
     * Send next date fact to user.
     * @param sendMessageOperation1Param
     * @param chatId
     */
    public void sendNextDateFact(
            final SendMessageOperation1Param sendMessageOperation1Param,
            final long chatId) {

        log.info("Sending date fact to user: {}", chatId);

        Mono<String> stringMono = getDateFactForThisDay();

        stringMono.subscribe(dataFact -> {

            SendMessage sendMessage
                    = StaticUtility.buildSendMessage(chatId, dataFact);

            CallbackProcessorService.andButtonToSendMessage(sendMessage);

            userService.deliverToUser(
                    sendMessageOperation1Param, sendMessage);
        });


        log.info("Date fact sent");

    }

}
