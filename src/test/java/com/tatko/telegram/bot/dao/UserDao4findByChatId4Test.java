package com.tatko.telegram.bot.dao;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.entity.User;
import com.tatko.telegram.bot.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class UserDao4findByChatId4Test extends MockitoExtensionBaseMockTests {

    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserDao userDao;

    @Test
    void save4successAndExist4Test() {

        // Before
        User user = getGen().nextUser();
        long chatId = getGen().nextLong();

        // When
        Mockito.when(userRepository.findByChatId(Mockito.eq(chatId)))
                .thenReturn(Optional.of(user));

        // Action
        Optional<User> userOptional = userDao.findByChatId(chatId);

        // Then
        assertThat(userOptional.isPresent())
                .isTrue();
        assertThat(userOptional.get())
                .isEqualTo(user);
        Mockito.verify(userRepository, Mockito.times(1))
                .findByChatId(Mockito.eq(chatId));
    }

    @Test
    void save4successAndNotExist4Test() {

        // Before
        User user = getGen().nextUser();
        long chatId = getGen().nextLong();

        // When
        Mockito.when(userRepository.findByChatId(Mockito.eq(chatId)))
                .thenReturn(Optional.empty());

        // Action
        Optional<User> userOptional = userDao.findByChatId(chatId);

        // Then
        assertThat(userOptional.isPresent())
                .isFalse();
        Mockito.verify(userRepository, Mockito.times(1))
                .findByChatId(Mockito.eq(chatId));
    }
}