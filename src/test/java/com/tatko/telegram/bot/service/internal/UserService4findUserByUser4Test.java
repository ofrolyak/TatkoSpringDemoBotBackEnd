package com.tatko.telegram.bot.service.internal;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.dao.UserDao;
import com.tatko.telegram.bot.entity.User;
import com.tatko.telegram.bot.entity.UserRole;
import com.tatko.telegram.bot.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.when;

class UserService4findUserByUser4Test extends MockitoExtensionBaseMockTests {

    @Mock
    UserDao userDao;
    @InjectMocks
    UserService userService;

    @Test
    void success4Test() {

        // Before
        User user = getGen().nextUser();

        // When
        when(userDao.findById(user.getId()))
                .thenReturn(Optional.of(user));

        // Action
        User userByUser = userService.findUserByUser(user);

        // Then
        assertThat(userByUser)
                .isEqualTo(user);
    }

    @Test
    void failure4UserNotFoundException4Test() {

        // Before
        User user = getGen().nextUser();

        // When
        when(userDao.findById(user.getId()))
                .thenThrow(UserNotFoundException.class);

        // Then
        assertThatCode(() -> userService.findUserByUser(user))
                .isInstanceOf(UserNotFoundException.class);
    }

}