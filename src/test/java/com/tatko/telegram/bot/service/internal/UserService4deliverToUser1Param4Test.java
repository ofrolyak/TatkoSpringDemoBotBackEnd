package com.tatko.telegram.bot.service.internal;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.service.custom.operation.SendMessageOperation1Param;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class UserService4deliverToUser1Param4Test extends MockitoExtensionBaseMockTests {

    @InjectMocks
    UserService userService;

    @Test
    void success4Test() {

        // Before
        SendMessageOperation1Param sendMessageOperation1Param
                = mock(SendMessageOperation1Param.class);
        SendMessage sendMessage = mock(SendMessage.class);

        // When
        doNothing()
                .when(sendMessageOperation1Param)
                .execute(eq(sendMessage));

        // Action
        userService.deliverToUser(sendMessageOperation1Param, sendMessage);

        // Then
        verify(sendMessageOperation1Param, times(1))
                .execute(eq(sendMessage));

    }

}