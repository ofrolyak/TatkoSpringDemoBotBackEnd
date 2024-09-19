package com.tatko.telegram.bot.service.internal;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.dao.UserDao;
import com.tatko.telegram.bot.entity.User;
import com.tatko.telegram.bot.util.BusinessUtility;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserService4registerUser4Test extends MockitoExtensionBaseMockTests {

    @Mock
    UserDao userDao;
    @Mock
    BusinessUtility businessUtility;
    @Spy
    @InjectMocks
    UserService userService;

    @Test
    void notExistUser4Test() {

        // Before
        Message message = getGen().nextMessage();
        User user = getGen().nextUser();

        // When
        when(userDao.findByChatId(anyLong()))
                .thenReturn(Optional.empty());
        doReturn(user)
                .when(businessUtility)
                .buildUserByMessage(message);

        // Action
        userService.registerUser(message);

        // Then
        verify(userDao, times(1))
                .save(eq(user));

    }

    @Test
    void existUser4Test() {

        // Before
        Message message = getGen().nextMessage();
        User user = getGen().nextUser();

        // When
        when(userDao.findByChatId(anyLong()))
                .thenReturn(Optional.of(user));

        // Action
        userService.registerUser(message);

        // Then
        verify(userDao, never())
                .save(eq(user));

    }


}