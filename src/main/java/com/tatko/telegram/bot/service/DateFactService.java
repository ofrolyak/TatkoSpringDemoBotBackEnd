package com.tatko.telegram.bot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
@Slf4j
public class DateFactService {

    @Autowired
    NumbersApiService numbersApiService;
    @Autowired
    UserService userService;

    private Mono<String> getDateFactForThisDay() {

        int day = LocalDate.now().getDayOfMonth();
        int month = LocalDate.now().getMonthValue();

        return numbersApiService.getDateFactForDay(month, day);

    }

    public void sendNextDateFact(Operation sendMessageOperation) {

        log.info("Sending ads to all users");

        Mono<String> stringMono = getDateFactForThisDay();

        stringMono.subscribe(dataFact -> userService.deliverToUsers(sendMessageOperation, dataFact));

        log.info("Ads sent");

    }

}
