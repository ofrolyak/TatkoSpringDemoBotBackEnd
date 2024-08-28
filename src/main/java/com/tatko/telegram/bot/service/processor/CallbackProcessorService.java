package com.tatko.telegram.bot.service.processor;

import com.tatko.telegram.bot.constant.Constant;
import com.tatko.telegram.bot.service.TelegramBotConfiguratorService;
import com.tatko.telegram.bot.service.business.DateFactService;
import com.tatko.telegram.bot.service.custom.operation.SendMessageOperation1Param;
import com.vdurmont.emoji.EmojiParser;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

import static com.tatko.telegram.bot.service.custom.button.InlineKeyButton.GET_NEXT_DATE_FACT;

@Service
@Slf4j
public class CallbackProcessorService {

    /**
     * Autowired by Spring DateFactService bean.
     */
    @Autowired
    private DateFactService dateFactService;

    /**
     * Autowired by Spring TelegramBotConfiguratorService bean.
     */
    @Autowired
    private TelegramBotConfiguratorService telegramBotConfiguratorService;

    /**
     * And Button To SendMessage.
     * @param sendMessage
     */
    public static void andButtonToSendMessage(final SendMessage sendMessage) {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        var inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setCallbackData(GET_NEXT_DATE_FACT.getLabel());
        inlineKeyboardButton.setText(EmojiParser.parseToUnicode(
                "Next Date Fact " + ":rolling_on_the_floor_laughing:"));

        rowInline.add(inlineKeyboardButton);
        rowsInline.add(rowInline);

        inlineKeyboardMarkup.setKeyboard(rowsInline);

        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
    }

    /**
     * Process received callback.
     *
     * @param update Update instance that has been gotten from Telegram user.
     */
    @SneakyThrows
    @Retryable(retryFor = TelegramApiException.class, maxAttempts = 2,
            backoff = @Backoff(delay = Constant.RETRYABLE_BACKOFF_DELAY))
    public void processReceivedCallback(final Update update) {


        final String callbackQuery = update.getCallbackQuery().getData();
        final int messageId = update.getCallbackQuery().getMessage()
                .getMessageId();
        final long chatId = update.getCallbackQuery().getMessage()
                .getChatId();

        if (callbackQuery.equals(GET_NEXT_DATE_FACT.getLabel())) {
            dateFactService
                    .sendNextDateFact(telegramBotConfiguratorService
                                    .getOperationByClass(
                                            SendMessageOperation1Param.class),
                            chatId);
        }


    }


}
