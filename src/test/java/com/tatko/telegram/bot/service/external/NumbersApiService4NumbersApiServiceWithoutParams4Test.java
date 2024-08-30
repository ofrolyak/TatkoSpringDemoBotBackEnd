package com.tatko.telegram.bot.service.external;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NumbersApiService4NumbersApiServiceWithoutParams4Test {

    @Test
    void success4Test() {

        // Action
        NumbersApiService numbersApiService = new NumbersApiService();

        // Then
        assertThat(numbersApiService.getWebClient())
            .isNotNull();

    }

}