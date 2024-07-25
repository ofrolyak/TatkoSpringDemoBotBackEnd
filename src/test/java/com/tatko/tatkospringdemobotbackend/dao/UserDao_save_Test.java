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

class UserDao_save_Test extends MockitoExtensionBaseMockTests {

    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserDao userDao;

    @Test
    void save_Test() {

        // Before
        User user = gen.nextObject(User.class);

        // When
        Mockito.when(userRepository.save(ArgumentMatchers.eq(user)))
                .thenReturn(user);

        // Then
        Assertions.assertThat(userDao.save(user))
                .isEqualTo(user);
    }

}