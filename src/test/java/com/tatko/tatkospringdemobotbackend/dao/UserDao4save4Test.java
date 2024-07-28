package com.tatko.tatkospringdemobotbackend.dao;

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
    void save_Test() {

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
