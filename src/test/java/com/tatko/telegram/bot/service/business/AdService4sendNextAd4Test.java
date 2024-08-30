package com.tatko.telegram.bot.service.business;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.dao.AdDao;
import com.tatko.telegram.bot.entity.Ad;
import com.tatko.telegram.bot.service.custom.operation.SendMessageOperation2Params;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class AdService4sendNextAd4Test extends MockitoExtensionBaseMockTests {

    @Mock
    AdDao adDao;
    @Spy
    @InjectMocks
    AdService adService;

    @Test
    void process4notPresentCase4Test() {

        // Before
        Ad ad = getGen().nextObject(Ad.class);
        SendMessageOperation2Params sendMessageOperation2Params
                = mock(SendMessageOperation2Params.class);

        // When
        doReturn(Optional.empty())
                .when(adDao)
                .findAdToDeliver(any(LocalDateTime.class));

        // Action
        adService.sendNextAd(sendMessageOperation2Params);

        // Then
        verify(adDao, times(1))
                .findAdToDeliver(any(LocalDateTime.class));
        verify(adService, never())
                .deliverAdToUsers(any(SendMessageOperation2Params.class),
                        any(Ad.class));

    }

    @Test
    void process4presentCase4Test() {

        // Before
        Ad ad = getGen().nextObject(Ad.class);
        SendMessageOperation2Params sendMessageOperation2Params
                = mock(SendMessageOperation2Params.class);

        // When
        doReturn(Optional.of(ad))
                .when(adDao)
                .findAdToDeliver(any(LocalDateTime.class));
        doNothing()
                .when(adService)
                .deliverAdToUsers(eq(sendMessageOperation2Params), eq(ad));

        // Action
        adService.sendNextAd(sendMessageOperation2Params);

        // Then
        verify(adDao, times(1))
                .findAdToDeliver(any(LocalDateTime.class));
        verify(adService, times(1))
                .deliverAdToUsers(any(SendMessageOperation2Params.class),
                        any(Ad.class));

    }

}
