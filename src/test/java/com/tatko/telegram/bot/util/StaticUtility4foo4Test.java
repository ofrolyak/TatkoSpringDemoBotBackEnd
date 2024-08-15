package com.tatko.telegram.bot.util;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

class StaticUtility4foo4Test extends MockitoExtensionBaseMockTests {

    @Test
    void foo4Test() {

        assertThatCode(() -> new StaticUtility().foo())
                .isInstanceOf(UnsupportedOperationException.class);

    }

}