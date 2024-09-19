package com.tatko.telegram.bot.service.internal;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.dao.UserArchDao;
import com.tatko.telegram.bot.dao.UserDao;
import com.tatko.telegram.bot.entity.User;
import com.tatko.telegram.bot.entity.UserArch;
import com.tatko.telegram.bot.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.BeanUtils;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class UserService4deleteUser4Test extends MockitoExtensionBaseMockTests {

    @Mock
    UserDao userDao;
    @Mock
    UserArchDao userArchDao;
    @Spy
    @InjectMocks
    private UserService userService;


    @Test
    void success4Test() {

        // Before
        User user = getGen().nextUser();
        UserArch userArch = new UserArch();
        BeanUtils.copyProperties(user, userArch);

        // When
        doReturn(user)
                .when(userService)
                .findUserByUser(eq(user));
        doReturn(userArch)
                .when(userArchDao)
                .save(eq(userArch));
        doNothing()
                .when(userDao)
                .delete(eq(user));

        // Action
        userService.deleteUser(user);

        // Verify
        verify(userService, times(1))
                .findUserByUser(eq(user));
        verify(userArchDao, times(1))
                .save(eq(userArch));
        verify(userDao, times(1))
                .delete(eq(user));

    }

    @Test
    void userNotFoundException4Test() {

        // Before
        User user = getGen().nextUser();
        UserArch userArch = new UserArch();
        BeanUtils.copyProperties(user, userArch);

        // When
        doThrow(UserNotFoundException.class)
                .when(userService)
                .findUserByUser(eq(user));

        // Action
        assertThatCode(() -> userService.deleteUser(user))
                .isInstanceOf(UserNotFoundException.class);

        // Verify
        verify(userService, times(1))
                .findUserByUser(eq(user));
        verify(userArchDao, times(0))
                .save(eq(userArch));
        verify(userDao, times(0))
                .delete(eq(user));

    }

}