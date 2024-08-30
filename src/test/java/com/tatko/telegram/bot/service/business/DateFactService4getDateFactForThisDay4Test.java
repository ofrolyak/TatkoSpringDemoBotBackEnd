package com.tatko.telegram.bot.service.business;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.service.external.NumbersApiService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class DateFactService4getDateFactForThisDay4Test extends MockitoExtensionBaseMockTests {

    @Mock
    NumbersApiService numbersApiService;
    @InjectMocks
    DateFactService dateFactService;

    @Test
    void getDateFactForThisDay() {

        // Before
        Mono<String> stringMono = Mono.empty();

        // When
        when(numbersApiService.getDateFactForDay(anyInt(), anyInt()))
                .thenReturn(stringMono);

        // Action
        Mono<String> dateFactForThisDay = dateFactService.getDateFactForThisDay();

        // Verify
        verify(numbersApiService, times(1))
                .getDateFactForDay(anyInt(), anyInt());
        assertThat(dateFactForThisDay)
                .isEqualTo(stringMono);

    }

}
