package com.tatko.telegram.bot.service;

import java.util.Optional;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.dao.UserDao;
import com.tatko.telegram.bot.entity.User;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
/**
 * JUnit class for TelegramBot class registerUser method.
 */

class TelegramBot4registerUser4Test
        extends MockitoExtensionBaseMockTests {

    /**
     * Mock for UserDao.
     */
    @Mock
    private UserDao userDao;

    /**
     * TelegramBot instance with injected mocks.
     */
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
        final User user = getGen().nextObject(User.class);

        // When
        Mockito.when(userDao.findByChatId(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(user));

        // Action
        telegramBot.registerUser(message);

        // Then
        Mockito.verify(userDao, Mockito.never())
                .save(ArgumentMatchers.any(User.class));

    }

    @Test
    void registerUser4userNotExists4Test() {

        // Before
        final Message message = new Message();
        final Chat chat = new Chat();
        chat.setId(0L);
        message.setChat(chat);
        final User user = getGen().nextObject(User.class);

        // When
        Mockito.when(userDao.findByChatId(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());
        Mockito.when(userDao.save(ArgumentMatchers.any(User.class)))
                .thenReturn(user);

        // Action
        telegramBot.registerUser(message);

        // Then
        Mockito.verify(userDao, Mockito.times(1))
                .save(ArgumentMatchers.any(User.class));

    }

}
