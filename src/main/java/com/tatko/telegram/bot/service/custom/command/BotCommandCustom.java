package com.tatko.telegram.bot.service.custom.command;

import com.tatko.telegram.bot.service.TelegramBotConfiguratorService;
import com.tatko.telegram.bot.service.custom.button.KeyButton;
import com.tatko.telegram.bot.service.custom.operation.SendMessageOperation3Params;
import com.tatko.telegram.bot.service.custom.storage.KeyButtonMapStorage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

/**
 * Service information for specific action.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Slf4j
public abstract class BotCommandCustom {

    /**
     * Action.
     */
    private String name;

    /**
     * Message text that referring for this action.
     */
    private String messageText;

    /**
     * Description.
     */
    private String description;

    /**
     * Do some action after receive update from Telegram user.
     *
     * @param update Received update from Telegram user.
     */
    public abstract void doAction(Update update);

    /**
     * Constructor itself.
     * @param nameInput
     * @param messageTextInput
     * @param descriptionInput
     */
    public BotCommandCustom(final String nameInput,
                            final String messageTextInput,
                            final String descriptionInput) {
        this.name = nameInput;
        this.messageText = messageTextInput;
        this.description = descriptionInput;
    }

    /**
     * Autowired by Spring KeyButtonMapStorage bean.
     */
    @Autowired
    private KeyButtonMapStorage keyButtonMapStorage;

    /**
     * Autowired by Spring TelegramBotConfiguratorService bean.
     */
    @Autowired
    private TelegramBotConfiguratorService telegramBotConfiguratorService;

    /**
     * Build list of buttons based on preferences.
     * @return List of String (button's names)
     */
    List<String> buildCollectionOfButtons() {

        log.debug("Process buildCollectionOfButtons");

        List<String> list = keyButtonMapStorage.getKeyButtonMap()
                .get(telegramBotConfiguratorService
                        .getServiceDataUserThreadLocal()
                        .get().getUserRole())
                .get(this.getClass())
                .stream()
                .map(KeyButton::getLabel)
                .toList();

        log.debug("Finished process buildCollectionOfButtons: {}", list);

        return list;
    }

    /**
     * Create ReplyKeyboardMarkup instance.
     * @return ReplyKeyboardMarkup instance.
     */
    ReplyKeyboardMarkup createReplyKeyboardMarkup() {

        log.debug("Process createReplyKeyboardMarkup");

        final ReplyKeyboardMarkup replyKeyboardMarkup
                = new ReplyKeyboardMarkup();
        final List<KeyboardRow> keyboardRowList = new ArrayList<>();
        final KeyboardRow keyboardRow1 = new KeyboardRow();

        List<String> keyButtonLabelList = buildCollectionOfButtons();

        keyboardRow1.addAll(keyButtonLabelList);

        keyboardRowList.add(keyboardRow1);

        replyKeyboardMarkup.setKeyboard(keyboardRowList);

        log.debug("Finished process createReplyKeyboardMarkup: {}",
                replyKeyboardMarkup);

        return replyKeyboardMarkup;
    }


    /**
     * Adding KeyBoard after getting update.
     *
     * @param update
     */
    void addKeyboardAndSendMessage(final Update update) {

        log.debug("Process addKeyboardAndSendMessage for update: {}", update);

        final long chatId = update.getMessage().getChatId();

        final ReplyKeyboardMarkup replyKeyboardMarkup
                = createReplyKeyboardMarkup();

        SendMessageOperation3Params sendMessageOperation
                = telegramBotConfiguratorService.getOperationByClass(
                SendMessageOperation3Params.class);

        sendMessageOperation.execute(chatId,
                "Invoking business request...",
                replyKeyboardMarkup);

        log.debug("Finished process addKeyboardAndSendMessage: {}",
                sendMessageOperation);

    }

}
