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
/**
 * JUnit class for UserDao class save method.
 */

class UserDao4save4Test extends MockitoExtensionBaseMockTests {

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
    void save4Test() {

        // Before
        final User user = getGen().nextObject(User.class);

        // When
        Mockito.when(userRepository.save(ArgumentMatchers.eq(user)))
                .thenReturn(user);

        // Then
        Assertions.assertThat(userDao.save(user))
                .isEqualTo(user);
    }

}
