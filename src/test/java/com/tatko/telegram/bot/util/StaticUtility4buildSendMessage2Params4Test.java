package com.tatko.telegram.bot.util;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static org.assertj.core.api.Assertions.assertThat;

class StaticUtility4buildSendMessage2Params4Test extends MockitoExtensionBaseMockTests {

    @Test
    void success4Test() {

        // Before
        final long chatId = getGen().nextLong();
        final String textMessage = getGen().nextString();

        // Action
        SendMessage sendMessage = StaticUtility.buildSendMessage(chatId, textMessage);

        // Then
        assertThat(sendMessage.getChatId())
                .isEqualTo("" + chatId);
        assertThat(sendMessage.getText())
                .isEqualTo(textMessage);

    }

}