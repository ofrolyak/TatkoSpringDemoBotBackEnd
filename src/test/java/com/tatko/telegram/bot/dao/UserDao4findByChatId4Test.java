package com.tatko.telegram.bot.dao;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.entity.User;
import com.tatko.telegram.bot.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;
/**
 * JUnit class for UserDao class findByChatId method.
 */

class UserDao4findByChatId4Test extends MockitoExtensionBaseMockTests {

    /**
     * User Repository itself.
     */
    @Mock
    private UserRepository userRepository;

    /**
     * User Dao itself.
     */
    @InjectMocks
    private UserDao userDao;

    @Test
    void findByChatId4UserIsNotFound4Test() {

        // Before
        final long id = getGen().nextInt();

        // When
        Mockito.when(userRepository.findByChatId(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        // Then
        Assertions.assertThat(userDao.findByChatId(id))
                .isEmpty();

    }

    @Test
    void findByChatId4UserIsFound4Test() {

        // Before
        final long id = getGen().nextInt();
        final User user = getGen().nextObject(User.class);

        // When
        Mockito.when(userRepository.findByChatId(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(user));

        // Then
        Assertions.assertThat(userDao.findByChatId(id).get())
                .isEqualTo(user);

    }

}
