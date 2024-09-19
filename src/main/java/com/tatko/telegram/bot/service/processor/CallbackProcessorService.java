package com.tatko.telegram.bot.service.processor;

import com.tatko.telegram.bot.service.TelegramBotConfiguratorService;
import com.tatko.telegram.bot.service.business.DateFactService;
import com.tatko.telegram.bot.service.custom.operation.SendMessageOperation1Param;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

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
     *
     * @param sendMessage
     */
    public static void andButtonToSendMessage(final SendMessage sendMessage) {

        log.debug("Process andButtonToSendMessage for sendMessage {}",
                sendMessage);

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

        log.debug("Finished process andButtonToSendMessage for sendMessage {}",
                sendMessage);

    }

    /**
     * Process received callback.
     *
     * @param update Update instance that has been gotten from Telegram user.
     */
    //@SneakyThrows
//    @Retryable(retryFor = TelegramApiException.class, maxAttempts = 2,
//            backoff = @Backoff(delay = Constant.RETRYABLE_BACKOFF_DELAY))
    public void processReceivedCallback(final Update update) {

        log.debug("Process processReceivedCallback for update {}", update);

        final String callbackQuery = update.getCallbackQuery().getData();
        final long chatId = update.getCallbackQuery().getMessage()
                .getChatId();

        if (callbackQuery.equals(GET_NEXT_DATE_FACT.getLabel())) {
            dateFactService
                    .sendNextDateFact(telegramBotConfiguratorService
                                    .getOperationByClass(
                                            SendMessageOperation1Param.class),
                            chatId);
        }

        log.debug("Finished process processReceivedCallback for update {}",
                update);

    }


}
