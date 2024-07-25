package com.tatko.tatkospringdemobotbackend.dao;

import java.util.Optional;

import com.tatko.tatkospringdemobotbackend.MockitoExtensionBaseMockTests;
import com.tatko.tatkospringdemobotbackend.entity.User;
import com.tatko.tatkospringdemobotbackend.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * JUnit class for UserDao class findByChatId method.
 */
class UserDao_findByChatId_Test extends MockitoExtensionBaseMockTests {

    /**
     * User Repository itself.
     */
    @Mock
    UserRepository userRepository;

    /**
     * User Dao itself.
     */
    @InjectMocks
    UserDao userDao;

    @Test
    void findByChatId4UserIsNotFound4Test() {

        // Before
        final long id = gen.nextInt();

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
        final long id = gen.nextInt();
        final User user = gen.nextObject(User.class);

        // When
        Mockito.when(userRepository.findByChatId(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(user));

        // Then
        Assertions.assertThat(userDao.findByChatId(id).get())
                .isEqualTo(user);

    }

}
