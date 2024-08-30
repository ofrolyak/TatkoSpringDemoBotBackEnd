package com.tatko.telegram.bot.service.internal;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.service.custom.operation.SendMessageOperation2Params;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class UserService4deliverToUser2Params4Test extends MockitoExtensionBaseMockTests {

    @InjectMocks
    UserService userService;

    @Test
    void success4Test() {

        // Before
        SendMessageOperation2Params sendMessageOperation2Params
                = mock(SendMessageOperation2Params.class);
        String testMessage = getGen().nextString();
        long chatId = getGen().nextLong();


        // When
        doNothing()
                .when(sendMessageOperation2Params)
                .execute(eq(chatId), eq(testMessage));

        // Action
        userService.deliverToUser(sendMessageOperation2Params, testMessage, chatId);

        // Then
        verify(sendMessageOperation2Params, times(1))
                .execute(eq(chatId), eq(testMessage));

    }

}