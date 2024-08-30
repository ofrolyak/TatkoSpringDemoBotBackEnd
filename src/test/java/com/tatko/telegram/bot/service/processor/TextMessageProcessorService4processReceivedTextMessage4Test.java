package com.tatko.telegram.bot.service.processor;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.dao.UserDao;
import com.tatko.telegram.bot.entity.User;
import com.tatko.telegram.bot.exception.UserNotFoundException;
import com.tatko.telegram.bot.service.business.AdService;
import com.tatko.telegram.bot.service.business.DateFactService;
import com.tatko.telegram.bot.service.custom.button.KeyButton;
import com.tatko.telegram.bot.service.custom.operation.SendMessageOperation2Params;
import com.tatko.telegram.bot.service.internal.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TextMessageProcessorService4processReceivedTextMessage4Test
        extends MockitoExtensionBaseMockTests {

    @Mock
    UserService userService;
    @Mock
    UserDao userDao;
    @Mock
    AdService adService;
    @Mock
    DateFactService dateFactService;
    @Mock
    SendMessageOperation2Params sendMessageOperation2Params;
    @Spy
    @InjectMocks
    TextMessageProcessorService textMessageProcessorService;

    @Test
    void processSendNextDateFact4Test() {

        // Before
        Update update = new Update();
        Message message = new Message();
        Chat chat = new Chat();
        chat.setId(getGen().nextLong());
        message.setChat(chat);
        message.setText(KeyButton.SEND_NEXT_DATE_FACT.getLabel());
        update.setMessage(message);

        // When
        doNothing()
                .when(dateFactService)
                .sendNextDateFact(eq(sendMessageOperation2Params));

        // Action
        textMessageProcessorService.processReceivedTextMessage(update, sendMessageOperation2Params);

        // Then
        verify(dateFactService, times(1))
                .sendNextDateFact(eq(sendMessageOperation2Params));
    }

    @Test
    void processSendAd4Test() {

        // Before
        Update update = new Update();
        Message message = new Message();
        Chat chat = new Chat();
        chat.setId(getGen().nextLong());
        message.setChat(chat);
        message.setText(KeyButton.SEND_AD.getLabel());
        update.setMessage(message);

        // When
        doNothing()
                .when(adService)
                .sendNextAd(eq(sendMessageOperation2Params));

        // Action
        textMessageProcessorService.processReceivedTextMessage(update, sendMessageOperation2Params);

        // Then
        verify(adService, times(1))
                .sendNextAd(eq(sendMessageOperation2Params));
    }

    @Test
    void processGetMyDataButUserNotFoundException4Test() {

        // Before
        Update update = new Update();
        Message message = new Message();
        Chat chat = new Chat();
        chat.setId(getGen().nextLong());
        message.setChat(chat);
        message.setText(KeyButton.GET_MY_DATA.getLabel());
        update.setMessage(message);

        // When
        when(userDao.findByChatId(chat.getId()))
                .thenReturn(Optional.empty());

        // Action
        assertThatCode(() -> textMessageProcessorService.processReceivedTextMessage(update, sendMessageOperation2Params))
                .isInstanceOf(UserNotFoundException.class);

        // Then
        verify(userService, never())
                .deliverToUser(eq(sendMessageOperation2Params), anyString(), any(User.class));
    }

    @Test
    void processGetMyData4Test() {

        // Before
        Update update = new Update();
        Message message = new Message();
        Chat chat = new Chat();
        chat.setId(getGen().nextLong());
        message.setChat(chat);
        message.setText(KeyButton.GET_MY_DATA.getLabel());
        update.setMessage(message);
        User user = getGen().nextObject(User.class);

        // When
        when(userDao.findByChatId(chat.getId()))
                .thenReturn(Optional.of(user));
        doNothing()
                .when(userService)
                .deliverToUser(eq(sendMessageOperation2Params), anyString(), eq(user));

        // Action
        textMessageProcessorService.processReceivedTextMessage(update, sendMessageOperation2Params);

        // Then
        verify(userService, times(1))
                .deliverToUser(eq(sendMessageOperation2Params), anyString(), any(User.class));
    }

    @Test
    void processDeleteMyDataButUserNotFoundException4Test() {

        // Before
        Update update = new Update();
        Message message = new Message();
        Chat chat = new Chat();
        chat.setId(getGen().nextLong());
        message.setChat(chat);
        message.setText(KeyButton.DELETE_MY_DATA.getLabel());
        update.setMessage(message);

        // When
        when(userDao.findByChatId(chat.getId()))
                .thenReturn(Optional.empty());

        // Action
        assertThatCode(() -> textMessageProcessorService.processReceivedTextMessage(update, sendMessageOperation2Params))
                .isInstanceOf(UserNotFoundException.class);

        // Then
        verify(userService, never())
                .deleteUser(any(User.class));
    }

    @Test
    void processDeleteMyData4Test() {

        // Before
        Update update = new Update();
        Message message = new Message();
        Chat chat = new Chat();
        chat.setId(getGen().nextLong());
        message.setChat(chat);
        message.setText(KeyButton.DELETE_MY_DATA.getLabel());
        update.setMessage(message);
        User user = getGen().nextObject(User.class);

        // When
        when(userDao.findByChatId(chat.getId()))
                .thenReturn(Optional.of(user));
        doNothing()
                .when(userService)
                .deleteUser(any(User.class));

        // Action
        textMessageProcessorService.processReceivedTextMessage(
                update, sendMessageOperation2Params);

        // Then
        verify(userService, times(1))
                .deleteUser(any(User.class));
    }

    @Test
    void processFakeKeyButtonLabel4Test() {

        // Before
        final Update update = new Update();
        final Message message = new Message();
        final Chat chat = new Chat();
        chat.setId(getGen().nextLong());
        message.setChat(chat);
        message.setText("fakeKeyButtonLabel");
        update.setMessage(message);

        // When
        doNothing()
                .when(textMessageProcessorService)
                .processReceivedCommand(eq(update), eq(sendMessageOperation2Params));

        // Action
        textMessageProcessorService.processReceivedTextMessage(
                update, sendMessageOperation2Params);

        // Then
        verify(textMessageProcessorService, times(1))
                .processReceivedCommand(eq(update), eq(sendMessageOperation2Params));
    }


}
