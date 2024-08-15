package com.tatko.telegram.bot.service.custom.command;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

class BotCommandCustom4createReplyKeyboardMarkup4Test extends MockitoExtensionBaseMockTests {


    BotCommandCustom botCommandCustom = Mockito.mock(
            BotCommandCustom.class,
            Mockito.CALLS_REAL_METHODS);

    @Test
    void success4empty4Test() {

        doReturn(List.of())
                .when(botCommandCustom)
                .buildCollectionOfButtons();

        ReplyKeyboardMarkup replyKeyboardMarkup = botCommandCustom.createReplyKeyboardMarkup();

        assertThat(replyKeyboardMarkup)
                .isNotNull();
        assertThat(replyKeyboardMarkup.getKeyboard())
                .hasSize(1);
        assertThat(replyKeyboardMarkup.getKeyboard().getFirst())
                .hasSize(0);

    }

    @Test
    void success4notEmpty4Test() {

        doReturn(List.of("1", "2", "3"))
                .when(botCommandCustom)
                .buildCollectionOfButtons();

        ReplyKeyboardMarkup replyKeyboardMarkup = botCommandCustom.createReplyKeyboardMarkup();

        assertThat(replyKeyboardMarkup)
                .isNotNull();
        assertThat(replyKeyboardMarkup.getKeyboard())
                .hasSize(1);
        assertThat(replyKeyboardMarkup.getKeyboard().getFirst())
                .hasSize(3);

    }


}