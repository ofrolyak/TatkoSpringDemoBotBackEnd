package com.tatko.telegram.bot.service;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Getter
public class KeyboardMarkupHolder {

    /**
     * Created and filled ReplyKeyboardMarkup instance.
     */
    public final static ReplyKeyboardMarkup replyKeyboardMarkupInstance
            = buildReplyKeyboardMarkup();

    public final static InlineKeyboardMarkup inlineKeyboardMarkup
            = buildInlineKeyboardMarkup();

    /**
     * Build ReplyKeyboardMarkup.
     *
     * @return ReplyKeyboardMarkup
     */
    private static ReplyKeyboardMarkup buildReplyKeyboardMarkup() {

        final ReplyKeyboardMarkup replyKeyboardMarkup
                = new ReplyKeyboardMarkup();
        final List<KeyboardRow> keyboardRowList = new ArrayList<>();
        final KeyboardRow keyboardRow1 = new KeyboardRow();
        keyboardRow1.add("weather");
        keyboardRow1.add("get random joke");
        keyboardRowList.add(keyboardRow1);

        final KeyboardRow keyboardRow2 = new KeyboardRow();
        keyboardRow2.add("register");
        keyboardRow2.add("check my data");
        keyboardRow2.add("delete my data");
        keyboardRowList.add(keyboardRow2);

        replyKeyboardMarkup.setKeyboard(keyboardRowList);

        return replyKeyboardMarkup;
    }


    /**
     * @return Created InlineKeyboardMarkup instance.
     */
    private static InlineKeyboardMarkup buildInlineKeyboardMarkup() {

        final InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        final List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        final List<InlineKeyboardButton> rowInLine = new ArrayList<>();

        final var yesButton = new InlineKeyboardButton();
        yesButton.setText("Yes");
        yesButton.setCallbackData("YES_BUTTON");

        final var noButton = new InlineKeyboardButton();
        noButton.setText("No");
        noButton.setCallbackData("NO_BUTTON");

        rowInLine.add(yesButton);
        rowInLine.add(noButton);

        rowsInLine.add(rowInLine);

        inlineKeyboardMarkup.setKeyboard(rowsInLine);

        return inlineKeyboardMarkup;
    }

}
