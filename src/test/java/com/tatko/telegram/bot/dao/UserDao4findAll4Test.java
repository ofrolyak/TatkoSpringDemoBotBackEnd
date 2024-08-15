package com.tatko.telegram.bot.dao;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.entity.User;
import com.tatko.telegram.bot.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserDao4findAll4Test extends MockitoExtensionBaseMockTests {

    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserDao userDao;

    @Test
    void save4success4notExist4Test() {

        // When
        Mockito.when(userRepository.findAll())
                .thenReturn(Collections.emptyList());

        // Action
        Collection<User> userCollection = userDao.findAll();

        // Then
        assertThat(userCollection)
                .isEmpty();
        Mockito.verify(userRepository, Mockito.times(1))
                .findAll();
    }

    @Test
    void save4success4exist4Test() {

        // Before
        User user = getGen().nextObject(User.class);

        // When
        Mockito.when(userRepository.findAll())
                .thenReturn(List.of(user));

        // Action
        List<User> userCollection = userDao.findAll();

        // Then
        assertThat(userCollection)
                .isNotEmpty();
        assertThat(userCollection)
                .hasSize(1);
        assertThat(userCollection.getFirst())
                .isEqualTo(user);
        Mockito.verify(userRepository, Mockito.times(1))
                .findAll();
    }

}