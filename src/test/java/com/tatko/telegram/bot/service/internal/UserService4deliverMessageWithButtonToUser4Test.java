package com.tatko.telegram.bot.service.internal;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.entity.User;
import com.tatko.telegram.bot.service.custom.operation.SendMessageOperation1Param;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class UserService4deliverMessageWithButtonToUser4Test
        extends MockitoExtensionBaseMockTests {

    @Spy
    @InjectMocks
    UserService userService;

    @Test
    void success4Test() {

        // Before
        SendMessageOperation1Param sendMessageOperation1Param
                = mock(SendMessageOperation1Param.class);
        String textMessage = getGen().nextObject(String.class);
        User user = getGen().nextUser();

        // When
        doNothing()
                .when(userService)
                .deliverToUser(eq(sendMessageOperation1Param),
                        any(SendMessage.class));

        // Action
        userService.deliverMessageWithButtonToUser(
                sendMessageOperation1Param, textMessage, user);

        // Then
        verify(userService, times(1))
                .deliverMessageWithButtonToUser(
                        eq(sendMessageOperation1Param),
                        eq(textMessage), any(User.class));

    }

}