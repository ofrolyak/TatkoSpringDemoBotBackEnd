package com.tatko.telegram.bot.util;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.doReturn;

class StaticUtility4readChatId4Test extends MockitoExtensionBaseMockTests {

    @Mock
    CallbackQuery callbackQueryMock;
    @Mock
    Message messageMock;
    @Mock
    Update updateMock;

    @Test
    void failure4messageIsNull4IllegalArgumentException4Test() {

        // When
        doReturn(false)
                .when(updateMock)
                .hasMessage();
        doReturn(false)
                .when(updateMock)
                .hasCallbackQuery();

        // Action
        assertThatCode(() -> StaticUtility.readChatId(updateMock))
                .isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    void failure4textIsNull4IllegalArgumentException4Test() {

        // When
        doReturn(true)
                .when(updateMock)
                .hasMessage();
        doReturn(messageMock)
                .when(updateMock)
                .getMessage();
        doReturn(false)
                .when(messageMock)
                .hasText();
        doReturn(false)
                .when(updateMock)
                .hasCallbackQuery();

        // Action
        assertThatCode(() -> StaticUtility.readChatId(updateMock))
                .isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    void success4messageCase4Test() {

        // Before
        doReturn(true)
                .when(updateMock)
                .hasMessage();
        doReturn(messageMock)
                .when(updateMock)
                .getMessage();
        doReturn(true)
                .when(messageMock)
                .hasText();
        long chatIdMock = getGen().nextLong();
        doReturn(chatIdMock)
                .when(messageMock)
                .getChatId();

        // Action
        long chatId = StaticUtility.readChatId(updateMock);

        // Then
        assertThat(chatId)
                .isEqualTo(chatIdMock);

    }

    @Test
    void success4callbackQueryCase4Test() {

        // Before
        doReturn(false)
                .when(updateMock)
                .hasMessage();
        doReturn(true)
                .when(updateMock)
                .hasCallbackQuery();
        doReturn(callbackQueryMock)
                .when(updateMock)
                .getCallbackQuery();
        doReturn(messageMock)
                .when(callbackQueryMock)
                .getMessage();
        long chatIdMock = getGen().nextLong();
        doReturn(chatIdMock)
                .when(messageMock)
                .getChatId();

        // Action
        long chatId = StaticUtility.readChatId(updateMock);

        // Then
        assertThat(chatId)
                .isEqualTo(chatIdMock);

    }

}
