package com.tatko.telegram.bot.service.internal;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.dao.UserDao;
import com.tatko.telegram.bot.entity.User;
import com.tatko.telegram.bot.service.custom.operation.SendMessageOperation1Param;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserService4deliverToUsers1Param4Test extends MockitoExtensionBaseMockTests {

    @Mock
    UserDao userDao;
    @Spy
    @InjectMocks
    UserService userService;

    @Test
    void noUsers4Test() {

        // Before
        SendMessageOperation1Param sendMessageOperation1Param
                = mock(SendMessageOperation1Param.class);
        String textMessage = getGen().nextObject(String.class);

        // When
        when(userDao.findAll())
                .thenReturn(Collections.emptyList());

        // Action
        userService.deliverToUsers(sendMessageOperation1Param, textMessage);

        // Then
        verify(userService, never())
                .deliverMessageWithButtonToUser(
                        any(SendMessageOperation1Param.class),
                        anyString(), any(User.class));

    }

    @Test
    void twoUsers4Test() {

        // Before
        SendMessageOperation1Param sendMessageOperation1Param
                = mock(SendMessageOperation1Param.class);
        String textMessage = getGen().nextObject(String.class);
        User user1 = getGen().nextUser();
        User user2 = getGen().nextUser();

        // When
        when(userDao.findAll())
                .thenReturn(List.of(user1, user2));
        doNothing()
                .when(userService)
                .deliverMessageWithButtonToUser(
                        eq(sendMessageOperation1Param),
                        eq(textMessage),
                        any(User.class));

        // Action
        userService.deliverToUsers(sendMessageOperation1Param, textMessage);

        // Then
        verify(userService, times(2))
                .deliverMessageWithButtonToUser(
                        any(SendMessageOperation1Param.class),
                        anyString(), any(User.class));

    }

}