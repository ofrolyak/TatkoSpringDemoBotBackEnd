package com.tatko.telegram.bot.service.custom.command;

import com.tatko.telegram.bot.service.TelegramBotService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class BotCommandCustomServiceAction extends BotCommandCustom {

    /**
     * Constructor.
     */
    public BotCommandCustomServiceAction() {
        super( "SERVICE",
                "/service", "business service");
    }

    public final static String SEND_NEXT_DATE_FACT = "send next date fact";
    public final static String SEND_AD = "send ad";

    @Override
    public void doAction(final TelegramBotService telegramBotService, final Update update) {

        final long chatId = update.getMessage().getChatId();



        final ReplyKeyboardMarkup replyKeyboardMarkup
                = new ReplyKeyboardMarkup();
        final List<KeyboardRow> keyboardRowList = new ArrayList<>();
        final KeyboardRow keyboardRow1 = new KeyboardRow();
        keyboardRow1.add("send next date fact");
        keyboardRow1.add("send ad");
        keyboardRowList.add(keyboardRow1);

//        final KeyboardRow keyboardRow2 = new KeyboardRow();
//        keyboardRow2.add("register");
//        keyboardRow2.add("check my data");
//        keyboardRow2.add("delete my data");
//        keyboardRowList.add(keyboardRow2);

        replyKeyboardMarkup.setKeyboard(keyboardRowList);

        telegramBotService.sendMessage(chatId,
                "This is bot for demonstration how to Spring Boot"
                        + " works with Telegram.",
                replyKeyboardMarkup);

    }

}
