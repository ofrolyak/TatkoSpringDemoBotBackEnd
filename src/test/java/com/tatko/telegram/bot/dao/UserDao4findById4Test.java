package com.tatko.telegram.bot.dao;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.entity.User;
import com.tatko.telegram.bot.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class UserDao4findById4Test extends MockitoExtensionBaseMockTests {

    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserDao userDao;

    @Test
    void userExists4Test() {

        // Before
        User user = getGen().nextUser();

        // When
        when(userRepository.findById(eq(user.getId())))
                .thenReturn(Optional.of(user));

        // Action
        Optional<User> userOptional = userDao.findById(user.getId());

        // Then
        assertThat(userOptional.isPresent())
                .isTrue();
        assertThat(userOptional.get())
                .isEqualTo(user);

    }

    @Test
    void userNotExists4Test() {

        // Before
        User user = getGen().nextUser();

        // When
        when(userRepository.findById(eq(user.getId())))
                .thenReturn(Optional.empty());

        // Action
        Optional<User> userOptional = userDao.findById(user.getId());

        // Then
        assertThat(userOptional.isPresent())
                .isFalse();

    }


}