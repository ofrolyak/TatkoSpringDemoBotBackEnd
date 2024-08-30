package com.tatko.telegram.bot.service.processor;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.service.custom.command.BotCommandCustomHelpAction;
import com.tatko.telegram.bot.service.custom.storage.BotCommandCustomSetStorage;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Set;

import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TextMessageProcessorService4onUpdateReceivedContains4Test extends MockitoExtensionBaseMockTests {

    @Mock
    BotCommandCustomHelpAction botCommandCustomHelpAction;
    @Mock
    BotCommandCustomSetStorage botCommandCustomSetStorage;
    @Spy
    @InjectMocks
    TextMessageProcessorService textMessageProcessorService;

    @Test
    void givenEmptyBotCommandCustomSet4Test() {

        // Before
        BotCommandCustomHelpAction botCommandCustomHelpAction
                = mock(BotCommandCustomHelpAction.class);
        Update update = new Update();
        Message message = new Message();
        Chat chat = new Chat();
        chat.setId(1L);
        message.setChat(chat);
        update.setMessage(message);

        // When
        when(botCommandCustomSetStorage.getBotCommandCustomSet())
                .thenReturn(Set.of());

        // Action
        textMessageProcessorService.onUpdateReceivedContains(update);

        // Then
        verify(botCommandCustomHelpAction, never())
                .doAction(eq(update));

    }

//    @Test
//    void given4Test() {
//
//        // Before
//        BotCommandCustomHelpAction botCommandCustomHelpAction
//                = mock(BotCommandCustomHelpAction.class);
//        Update update = new Update();
//        Message message = new Message();
//        Chat chat = new Chat();
//        chat.setId(1L);
//        message.setChat(chat);
//        message.setText(new BotCommandCustomHelpAction().getMessageText() + SPACE + "blablabla");
//        update.setMessage(message);
//
//        // When
//        when(botCommandCustomSetStorage.getBotCommandCustomSet())
//                .thenReturn(Set.of(new BotCommandCustomHelpAction()));
//        doNothing()
//                .when(botCommandCustomHelpAction)
//                .doAction(eq(update));
//
//        // Action
//        textMessageProcessorService.onUpdateReceivedContains(update);
//
//        // Then
//        verify(botCommandCustomHelpAction, times(1))
//                .doAction(eq(update));
//
//    }

    @Test
    void g1iven4Test() {

        // Before
        Update update = new Update();
        Message message = new Message();
        Chat chat = new Chat();
        chat.setId(1L);
        message.setChat(chat);
        message.setText(new BotCommandCustomHelpAction().getMessageText() + SPACE + "blablabla");
        update.setMessage(message);

        // When
        when(botCommandCustomSetStorage.getBotCommandCustomSet())
                .thenReturn(Set.of(botCommandCustomHelpAction));
        when(botCommandCustomHelpAction.getMessageText())
                .thenReturn(new BotCommandCustomHelpAction().getMessageText());
        doNothing()
                .when(botCommandCustomHelpAction)
                .doAction(eq(update));

        // Action
        textMessageProcessorService.onUpdateReceivedContains(update);

        // Then
        verify(botCommandCustomHelpAction, times(1))
                .doAction(eq(update));

    }

    @Test
    void g1iven44Test() {

        // Before
        Update update = new Update();
        Message message = new Message();
        Chat chat = new Chat();
        chat.setId(1L);
        message.setChat(chat);
        message.setText(new BotCommandCustomHelpAction().getMessageText() + SPACE);
        update.setMessage(message);

        // When
        when(botCommandCustomSetStorage.getBotCommandCustomSet())
                .thenReturn(Set.of(botCommandCustomHelpAction));
        when(botCommandCustomHelpAction.getMessageText())
                .thenReturn(new BotCommandCustomHelpAction().getMessageText());

        // Action
        textMessageProcessorService.onUpdateReceivedContains(update);

        // Then
        verify(botCommandCustomHelpAction, times(0))
                .doAction(eq(update));

    }


}