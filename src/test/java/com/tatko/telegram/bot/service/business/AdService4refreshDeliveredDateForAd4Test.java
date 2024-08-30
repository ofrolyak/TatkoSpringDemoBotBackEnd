package com.tatko.telegram.bot.service.business;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.dao.AdDao;
import com.tatko.telegram.bot.entity.Ad;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class AdService4refreshDeliveredDateForAd4Test extends MockitoExtensionBaseMockTests {

    @Mock
    AdDao adDao;
    @InjectMocks
    AdService adService;

    @Test
    void process4Test() {

        // Before
        Ad ad = mock(Ad.class);

        // When
        doReturn(ad)
                .when(adDao)
                .save(eq(ad));

        // Action
        adService.refreshDeliveredDateForAd(ad);

        // Then
        verify(ad, times(1))
                .setDeliveredTime(any(LocalDateTime.class));
        verify(adDao, times(1))
                .save(eq(ad));

    }

}
