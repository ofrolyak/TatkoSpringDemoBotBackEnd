package com.tatko.telegram.bot.audit;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class OnUpdateInitProcessor4auditPoint4Test extends MockitoExtensionBaseMockTests {

    @Spy
    @InjectMocks
    OnUpdateInitProcessor onUpdateInitProcessor;

    @Test
    void success() {

        // Action
        onUpdateInitProcessor.auditPoint();

        // When
        verify(onUpdateInitProcessor, times(1))
                .auditPoint();

    }

}