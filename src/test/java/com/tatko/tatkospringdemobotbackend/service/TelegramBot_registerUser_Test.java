package com.tatko.tatkospringdemobotbackend.service;

import com.tatko.tatkospringdemobotbackend.MockitoExtensionBaseMockTests;
import com.tatko.tatkospringdemobotbackend.dao.UserDao;
import com.tatko.tatkospringdemobotbackend.entity.User;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

/**
 * JUnit class for TelegramBot class registerUser method.
 */
class TelegramBot_registerUser_Test
        extends MockitoExtensionBaseMockTests {

    @Mock
    private UserDao userDao;
    @Spy
    @InjectMocks
    private TelegramBot telegramBot;


    @Test
    void registerUser4userExists4Test() {

        // Before
        final Message message = new Message();
        final Chat chat = new Chat();
        chat.setId(0L);
        message.setChat(chat);
        final User user = gen.nextObject(User.class);

        // When
        Mockito.when(userDao.findByChatId(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(user));

        // Action
        telegramBot.registerUser(message);

        // Then
        Mockito.verify(userDao, Mockito.never()).save(ArgumentMatchers.any(User.class));

    }

    @Test
    void registerUser4userNotExists4Test() {

        // Before
        final Message message = new Message();
        final Chat chat = new Chat();
        chat.setId(0L);
        message.setChat(chat);
        final User user = gen.nextObject(User.class);

        // When
        Mockito.when(userDao.findByChatId(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());
        Mockito.when(userDao.save(ArgumentMatchers.any(User.class)))
                .thenReturn(user);

        // Action
        telegramBot.registerUser(message);

        // Then
        Mockito.verify(userDao, Mockito.times(1)).save(ArgumentMatchers.any(User.class));

    }

}
