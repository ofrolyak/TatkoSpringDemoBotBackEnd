package com.tatko.telegram.bot.service.internal;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.entity.User;
import com.tatko.telegram.bot.service.custom.operation.SendMessageOperation2Params;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class UserService4deliverToUser2ParamsWith2Params4Test extends MockitoExtensionBaseMockTests {

    @Spy
    @InjectMocks
    UserService userService;

    @Test
    void success4Test() {

        // Before
        SendMessageOperation2Params sendMessageOperation2Params
                = mock(SendMessageOperation2Params.class);
        String testMessage = getGen().nextString();
        User user = getGen().nextUser();

        // When
        doNothing()
                .when(userService)
                .deliverToUser(eq(sendMessageOperation2Params),
                        eq(testMessage), eq(user.getChatId()));

        // Action
        userService.deliverToUser(sendMessageOperation2Params, testMessage, user);

        // Then
        verify(userService, times(1))
                .deliverToUser(eq(sendMessageOperation2Params),
                        eq(testMessage), eq(user.getChatId()));

    }

}