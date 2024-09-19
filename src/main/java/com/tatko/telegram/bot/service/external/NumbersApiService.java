package com.tatko.telegram.bot.service.external;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Getter
@Slf4j
public class NumbersApiService {

    /**
     * WebClient instance.
     */
    private final WebClient webClient;

//    /**
//     * Constructor itself.
//     * @param webClientBuilder
//     */
//    public NumbersApiService(final WebClient.Builder webClientBuilder) {
//        this.webClient
//                = webClientBuilder.baseUrl("http://numbersapi.com").build();
//    }

    /**
     * Constructor itself.
     *
     * @param webClientParam
     */
    public NumbersApiService(final WebClient webClientParam) {
        this.webClient = webClientParam;
    }

    /**
     * Constructor itself.
     */
    public NumbersApiService() {
        log.info("Initializing NumbersApiService");
        this.webClient = WebClient.builder()
                .baseUrl("http://numbersapi.com")
                .build();
        log.info("Finished initializing NumbersApiService");
    }

    /**
     * Read from remote API date fact.
     * @param month
     * @param day
     * @return Mono instance of date fact.
     */
    public Mono<String> getDateFactForDay(final int month, final int day) {
        log.debug("Process getDateFactForDay");
        Mono<String> stringMono = this.webClient
                .get()
                .uri("/{month}/{day}/date", month, day)
                .retrieve()
                .bodyToMono(String.class);
        log.debug("Finished process getDateFactForDay: {}", stringMono);
        return stringMono;
    }

}
