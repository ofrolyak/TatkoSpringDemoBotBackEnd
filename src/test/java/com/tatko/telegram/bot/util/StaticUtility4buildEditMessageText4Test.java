package com.tatko.telegram.bot.util;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

import static org.assertj.core.api.Assertions.assertThat;

class StaticUtility4buildEditMessageText4Test extends MockitoExtensionBaseMockTests {

    @Test
    void success4Test() {

        // Before
        final long chatId = getGen().nextLong();
        final String textMessage = getGen().nextString();
        final int messageId = getGen().nextInt();

        // Action
        EditMessageText editMessageText = StaticUtility.buildEditMessageText(chatId, textMessage, messageId);

        // Then
        assertThat(editMessageText.getChatId())
                .isEqualTo("" + chatId);
        assertThat(editMessageText.getText())
                .isEqualTo(textMessage);
        assertThat(editMessageText.getMessageId())
                .isEqualTo(messageId);

    }

}