package com.tatko.telegram.bot.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class NumbersApiService {

    private final WebClient webClient;

    public NumbersApiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://numbersapi.com").build();
    }

    public Mono<String> getDateFactForDay(int month, int day) {
        return this.webClient.get().uri("/{month}/{day}/date", month, day).retrieve().bodyToMono(String.class);
    }

}
