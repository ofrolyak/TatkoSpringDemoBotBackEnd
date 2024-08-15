package com.tatko.telegram.bot.service.external;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class NumbersApiService {

    /**
     * WebClient instance.
     */
    private final WebClient webClient;

    /**
     * Constructor itself.
     * @param webClientBuilder
     */
    public NumbersApiService(final WebClient.Builder webClientBuilder) {
        this.webClient
                = webClientBuilder.baseUrl("http://numbersapi.com").build();
    }

    /**
     * Read from remote API date fact.
     * @param month
     * @param day
     * @return Mono instance of date fact.
     */
    public Mono<String> getDateFactForDay(final int month, final int day) {
        return this.webClient
                .get()
                .uri("/{month}/{day}/date", month, day)
                .retrieve()
                .bodyToMono(String.class);
    }

}
