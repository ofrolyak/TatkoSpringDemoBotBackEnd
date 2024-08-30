package com.tatko.telegram.bot.service.processor;

import com.tatko.telegram.bot.dao.UserDao;
import com.tatko.telegram.bot.entity.User;
import com.tatko.telegram.bot.exception.UserNotFoundException;
import com.tatko.telegram.bot.service.business.AdService;
import com.tatko.telegram.bot.service.business.DateFactService;
import com.tatko.telegram.bot.service.custom.button.KeyButton;
import com.tatko.telegram.bot.service.custom.command.BotCommandCustom;
import com.tatko.telegram.bot.service.custom.operation.SendMessageOperation2Params;
import com.tatko.telegram.bot.service.custom.storage.BotCommandCustomSetStorage;
import com.tatko.telegram.bot.service.internal.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Service
@Slf4j
public class TextMessageProcessorService {

    /**
     * Autowired by Spring DateFactService bean.
     */
    @Autowired
    private DateFactService dateFactService;

    /**
     * Autowired by Spring AdService bean.
     */
    @Autowired
    private AdService adService;

    /**
     * Autowired by Spring UserDao bean.
     */
    @Autowired
    private UserDao userDao;

    /**
     * Autowired by Spring UserService bean.
     */
    @Autowired
    private UserService userService;

    /**
     * Autowired by Spring BotCommandCustomSetStorage bean.
     */
    @Autowired
    private BotCommandCustomSetStorage botCommandCustomSetStorage;

    /**
     * Based on input text message from user parse it
     * and return specific structure class.
     *
     * @param messageText Message text from Telegram bot.
     * @return Specific structure class
     */
    public Optional<BotCommandCustom> parseBotCommandCustom(
            final String messageText) {
        return botCommandCustomSetStorage.getBotCommandCustomSet().stream()
                .filter(botCommand
                        -> botCommand.getMessageText().equals(messageText))
                .findFirst();
    }

    /**
     * Accept passed botCommandCustom and launch actions based on it.
     *
     * @param botCommandCustom BotCommandCustom instance
     *                         that should be executed.
     * @param update           Update instance from Telegram Bot.
     */
    public void acceptBotCommandCustom(
            @NotNull final BotCommandCustom botCommandCustom,
            final @NotNull Update update) {
        botCommandCustom.doAction(update);
    }

    /**
     * Process received message.
     *
     * @param update Update instance that has been gotten from Telegram user.
     * @param sendMessageOperation2Params
     */
    public void processReceivedMessage(
            final Update update,
            final SendMessageOperation2Params sendMessageOperation2Params) {

        final long chatId = update.getMessage().getChatId();
        final String messageText = update.getMessage().getText();

        final Optional<BotCommandCustom> botCommandCustomOptional
                = parseBotCommandCustom(messageText);

        // Parse text message from Telegram User
        botCommandCustomOptional.ifPresentOrElse(_
                        -> acceptBotCommandCustom(
                        botCommandCustomOptional.get(), update),
                () -> sendMessageOperation2Params.execute(chatId,
                        "Головне в нашому житті - не тупікувати!!!"));

    }

    /**
     * Process Direct case.
     *
     * @param update                      Update instance.
     * @param sendMessageOperation2Params
     */
    void onUpdateReceivedDirect(
            final Update update,
            final SendMessageOperation2Params sendMessageOperation2Params) {

//        Optional<BotCommandCustom> firstEqual
//                = botCommandCustomSetStorage.getBotCommandCustomSet()
//                .stream()
//                .filter(botCommandCustom
//                        -> update.getMessage().getText()
//                        .equals(botCommandCustom.getMessageText()))
//                .findFirst();

        Optional<BotCommandCustom> botCommandCustomOptional
                = parseBotCommandCustom(update.getMessage().getText());

        if (botCommandCustomOptional.isPresent()) {
            // Text message
            processReceivedMessage(update, sendMessageOperation2Params);
        }

    }

    private boolean isCondition(
            final Update update, final BotCommandCustom botCommandCustom) {
        boolean condition1 = update.getMessage().getText()
                .startsWith(botCommandCustom.getMessageText());
        boolean condition2 = update.getMessage().getText().length()
                > botCommandCustom.getMessageText().length() + 1;
        return condition1 && condition2;
    }

    /**
     * Process Contains case.
     *
     * @param update Update instance.
     */
    void onUpdateReceivedContains(final Update update) {

        Optional<BotCommandCustom> botCommandCustomOptional
                = botCommandCustomSetStorage.getBotCommandCustomSet().stream()
                .filter(botCommandCustom
                        -> isCondition(update, botCommandCustom))
                .findFirst();

        botCommandCustomOptional.ifPresent(botCommandCustom
                -> botCommandCustom.doAction(update));

    }

    /**
     * Process received command message based on update instance.
     *
     * @param update
     * @param sendMessageOperation2Params
     */
    //@SneakyThrows
//    @Retryable(retryFor = TelegramApiException.class, maxAttempts = 2,
//            backoff = @Backoff(delay = Constant.RETRYABLE_BACKOFF_DELAY))
    public void processReceivedCommand(
            final Update update,
            final SendMessageOperation2Params sendMessageOperation2Params) {
        // direct case
        onUpdateReceivedDirect(update, sendMessageOperation2Params);
        // contains case
        onUpdateReceivedContains(update);
    }

    /**
     * Process received text message based on update instance.
     *
     * @param update
     * @param sendMessageOperation2Params
     */
    public void processReceivedTextMessage(
            final Update update,
            final SendMessageOperation2Params sendMessageOperation2Params) {
        if (update.getMessage().getText()
                .equals(KeyButton.SEND_NEXT_DATE_FACT.getLabel())) {
            dateFactService.sendNextDateFact(sendMessageOperation2Params);
        } else if (update.getMessage().getText()
                .equals(KeyButton.SEND_AD.getLabel())) {
            adService.sendNextAd(sendMessageOperation2Params);
        } else if (update.getMessage().getText()
                .equals(KeyButton.GET_MY_DATA.getLabel())) {
            User user = userDao.findByChatId(update.getMessage().getChatId())
                    .orElseThrow(UserNotFoundException::new);
            userService.deliverToUser(
                    sendMessageOperation2Params, user.toString(), user);
        } else if (update.getMessage().getText()
                .equals(KeyButton.DELETE_MY_DATA.getLabel())) {
            User user = userDao.findByChatId(update.getMessage().getChatId())
                    .orElseThrow(UserNotFoundException::new);
            userService.deleteUser(user);
        } else {
            // Command message
            processReceivedCommand(update, sendMessageOperation2Params);
        }
    }


}
