package com.tatko.telegram.bot.dao;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.entity.User;
import com.tatko.telegram.bot.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

class UserDao4save4Test extends MockitoExtensionBaseMockTests {

    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserDao userDao;

    @Test
    void save4success4Test() {

        // Before
        User user = getGen().nextUser();

        // When
        Mockito.when(userRepository.save(Mockito.eq(user)))
                .thenReturn(user);

        // Action
        User userSaved = userDao.save(user);

        // Then
        assertThat(userSaved)
                .isEqualTo(user);
        Mockito.verify(userRepository, Mockito.times(1))
                .save(Mockito.eq(user));
    }

}