package com.tatko.telegram.bot.dao;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.entity.User;
import com.tatko.telegram.bot.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

class UserDao4delete4Test extends MockitoExtensionBaseMockTests {

    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserDao userDao;

    @Test
    void save4successAndExist4Test() {

        // Before
        User user = getGen().nextUser();

        // When
        Mockito.doNothing()
                .when(userRepository)
                .delete(Mockito.eq(user));

        // Action
        userDao.delete(user);

        // Then
        Mockito.verify(userRepository, Mockito.times(1))
                .delete(Mockito.eq(user));
    }

}