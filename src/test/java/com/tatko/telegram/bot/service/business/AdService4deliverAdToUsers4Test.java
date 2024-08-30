package com.tatko.telegram.bot.service.business;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.entity.Ad;
import com.tatko.telegram.bot.service.custom.operation.SendMessageOperation2Params;
import com.tatko.telegram.bot.service.internal.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class AdService4deliverAdToUsers4Test extends MockitoExtensionBaseMockTests {

    @Mock
    UserService userService;
    @Spy
    @InjectMocks
    AdService adService;

    @Test
    void process4Test() {

        // Before
        Ad ad = getGen().nextObject(Ad.class);
        SendMessageOperation2Params sendMessageOperation2Params = mock(SendMessageOperation2Params.class);

        // When
        doNothing()
                .when(userService)
                .deliverToUsers(any(SendMessageOperation2Params.class), anyString());
        doNothing()
                .when(adService)
                .refreshDeliveredDateForAd(eq(ad));

        // Action
        adService.deliverAdToUsers(sendMessageOperation2Params, ad);

        // Then
        verify(userService, times(1))
                .deliverToUsers(eq(sendMessageOperation2Params), anyString());
        verify(adService, times(1))
                .refreshDeliveredDateForAd(eq(ad));

    }

}
