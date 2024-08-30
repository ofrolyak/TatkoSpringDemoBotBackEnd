package com.tatko.telegram.bot.service.external;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class NumbersApiService4getDateFactForDay4Test extends MockitoExtensionBaseMockTests {

    @InjectMocks
    NumbersApiService numbersApiService;
    @Mock
    private WebClient webClientMock;
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersMock;
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriMock;
    @Mock
    private WebClient.ResponseSpec responseMock;

    @BeforeEach
    void setUp() {
        numbersApiService = new NumbersApiService(webClientMock);
    }

    @Test
    void success4Test() {

        // Before
        String nextDateFact = getGen().nextString();

        // When
        when(webClientMock.get())
                .thenReturn(requestHeadersUriMock);
        when(requestHeadersUriMock
                .uri(eq("/{month}/{day}/date"), eq(new Object[] {1, 1})))
                .thenReturn(requestHeadersMock);
        when(requestHeadersMock.retrieve())
                .thenReturn(responseMock);
        when(responseMock.bodyToMono(eq(String.class)))
                .thenReturn(Mono.just(nextDateFact));

        // Action
        Mono<String> employeeMono = numbersApiService.getDateFactForDay(1,1);

        // Then
        StepVerifier.create(employeeMono)
                .expectNextMatches(s -> s.equals(nextDateFact))
                .verifyComplete();

    }

}
