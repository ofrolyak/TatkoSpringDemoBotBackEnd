package com.tatko.tatkospringdemobotbackend.service;

import com.tatko.tatkospringdemobotbackend.BaseMockTests;
import com.tatko.tatkospringdemobotbackend.dao.UserDao;
import com.tatko.tatkospringdemobotbackend.entity.User;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class TelegramBot_registerUser_Test extends BaseMockTests {

    @Mock
    UserDao userDao;
    @InjectMocks
    TelegramBot telegramBot;

    @Test
    void registerUser_userExists_Test() {

        // Before
        Message message = new Message();
        Chat chat = new Chat();
        chat.setId(0L);
        message.setChat(chat);
        User user = gen.nextObject(User.class);

        // When
        when(userDao.findByChatId(anyLong()))
                .thenReturn(Optional.of(user));

        // Action
        telegramBot.registerUser(message);

        // Then
        verify(userDao, never()).save(any(User.class));

    }

    @Test
    void registerUser_userNotExists_Test() {

        // Before
        Message message = new Message();
        Chat chat = new Chat();
        chat.setId(0L);
        message.setChat(chat);
        User user = gen.nextObject(User.class);

        // When
        when(userDao.findByChatId(anyLong()))
                .thenReturn(Optional.empty());

        // Action
        telegramBot.registerUser(message);

        // Then
        verify(userDao, times(1)).save(any(User.class));

    }

}