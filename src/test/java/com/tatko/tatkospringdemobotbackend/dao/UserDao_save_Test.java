package com.tatko.tatkospringdemobotbackend.dao;

import com.tatko.tatkospringdemobotbackend.BaseMockTests;
import com.tatko.tatkospringdemobotbackend.MockitoExtensionBaseMockTests;
import com.tatko.tatkospringdemobotbackend.entity.User;
import com.tatko.tatkospringdemobotbackend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

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
        when(userRepository.save(eq(user)))
                .thenReturn(user);

        // Then
        assertThat(userDao.save(user))
                .isEqualTo(user);
    }

}