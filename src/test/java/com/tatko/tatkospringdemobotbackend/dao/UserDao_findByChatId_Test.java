package com.tatko.tatkospringdemobotbackend.dao;

import com.tatko.tatkospringdemobotbackend.BaseMockTests;
import com.tatko.tatkospringdemobotbackend.MockitoExtensionBaseMockTests;
import com.tatko.tatkospringdemobotbackend.entity.User;
import com.tatko.tatkospringdemobotbackend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class UserDao_findByChatId_Test extends MockitoExtensionBaseMockTests {

    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserDao userDao;

    @Test
    void findByChatId_UserIsNotFound_Test() {

        // Before
        long id = gen.nextInt();

        // When
        when(userRepository.findByChatId(anyLong()))
                .thenReturn(Optional.empty());

        // Then
        assertThat(userDao.findByChatId(id))
                .isEmpty();

    }

    @Test
    void findByChatId_UserIsFound_Test() {

        // Before
        long id = gen.nextInt();
        User user = gen.nextObject(User.class);

        // When
        when(userRepository.findByChatId(anyLong()))
                .thenReturn(Optional.of(user));

        // Then
        assertThat(userDao.findByChatId(id).get())
                .isEqualTo(user);

    }

}