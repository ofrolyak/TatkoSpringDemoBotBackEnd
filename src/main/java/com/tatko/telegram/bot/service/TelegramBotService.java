package com.tatko.telegram.bot.service;

import com.tatko.telegram.bot.BusinessUtility;
import com.tatko.telegram.bot.StaticUtility;
import com.tatko.telegram.bot.config.TelegramBotConfig;
import com.tatko.telegram.bot.constant.Constant;
import com.tatko.telegram.bot.dao.UserDao;
import com.tatko.telegram.bot.dao.UserRoleDao;
import com.tatko.telegram.bot.service.custom.command.BotCommandCustom;
import com.tatko.telegram.bot.service.custom.command.BotCommandCustomHelpAction;
import com.tatko.telegram.bot.service.custom.command.BotCommandCustomServiceAction;
import com.tatko.telegram.bot.service.custom.command.BotCommandCustomSettingsAction;
import com.tatko.telegram.bot.service.custom.command.BotCommandCustomStartAction;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.tatko.telegram.bot.service.custom.command.BotCommandCustomServiceAction.SEND_AD;
import static com.tatko.telegram.bot.service.custom.command.BotCommandCustomServiceAction.SEND_NEXT_DATE_FACT;

/**
 * Telegram Bot service.
 */

@Component
@Slf4j
public class TelegramBotService extends TelegramLongPollingBot {

    public TelegramBotService(@Value("${telegram.bot.token}") String botToken) {
        super(botToken);
    }

    @Autowired
    BusinessUtility businessUtility;

    /**
     * Created and filled collection with BotCommandCustom instances.
     */
//    @Getter
//    Map<UserRole, Set<BotCommandCustom>> botCommandsMap;

    @Autowired
    ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        log.debug("PostConstruct.init() started!");
        //botCommandsMap = businessUtility.buildBotCommandsMap();


        botCommandsSet = new HashSet<>();
        botCommandsSet.add(applicationContext.getBean(BotCommandCustomStartAction.class));
        botCommandsSet.add(applicationContext.getBean(BotCommandCustomSettingsAction.class));
        botCommandsSet.add(applicationContext.getBean(BotCommandCustomServiceAction.class));
        botCommandsSet.add(applicationContext.getBean(BotCommandCustomHelpAction.class));

        log.debug("PostConstruct.init() finished!");


    }

    /**
     * TelegramBotConfig itself .
     */
    @Autowired
    private TelegramBotConfig telegramBotConfig;


    /**
     * Get Bot UserName.
     *
     * @return Bot UserName
     */
    @Override
    public String getBotUsername() {
        return telegramBotConfig.getTelegramBotName();
    }

    /**
     * Send message without keyboard.
     *
     * @param chatId  Identifier for Telegram chat.
     * @param message Prepared message instance for Telegram chat.
     */
    @SneakyThrows
    @Retryable(retryFor = TelegramApiException.class, maxAttempts = 2,
            backoff = @Backoff(delay = Constant.RETRYABLE_BACKOFF_DELAY))
    public void sendMessage(final long chatId, final String message) {

        log.info("Sending message: {}", message);

        execute(StaticUtility.buildSendMessage(chatId, message));

    }

    /**
     * Send message with keyboard.
     *
     * @param chatId              Identifier for Telegram chat.
     * @param message             Prepared message instance for Telegram chat.
     * @param replyKeyboardMarkup ReplyKeyboardMarkup instance.
     */
    @SneakyThrows
    @Retryable(retryFor = TelegramApiException.class, maxAttempts = 2,
            backoff = @Backoff(delay = Constant.RETRYABLE_BACKOFF_DELAY))
    public void sendMessage(final long chatId, final String message,
                            final ReplyKeyboardMarkup replyKeyboardMarkup) {

        log.info("Sending message: {}", message);

        execute(StaticUtility.buildSendMessage(
                chatId, message, replyKeyboardMarkup));

    }

    Set<BotCommandCustom> botCommandsSet;

    /**
     * Build BotCommandList.
     *
     * @return List of BotCommand
     */
    public List<BotCommand> buildBotCommands() {
        return botCommandsSet
                .stream()
                .map(botCommandCustom -> new BotCommand(
                        botCommandCustom.getMessageText(),
                        botCommandCustom.getDescription()))
                .toList();
    }

    /**
     * Add prepared bot commands to Telegram bot.
     */
    @SneakyThrows
    public void addPreparedBotCommandsToBot() {
        final List<BotCommand> botCommandList = buildBotCommands();
        addBotCommandListToBot(botCommandList);
    }


    /**
     * Add prepared bot commands to Telegram bot.
     */
    @SneakyThrows
    public void addBotCommandListToBot(final List<BotCommand> botCommandList) {


        execute(new SetMyCommands(botCommandList, new BotCommandScopeDefault(),
                null));
    }

    @SneakyThrows
    public void editMessage(EditMessageText buildEditMessageText) {
        execute(buildEditMessageText);

    }

    ThreadLocal<Set<BotCommandCustom>> threadLocal = new ThreadLocal<>();


    /**
     * Based on input text message from user parse it
     * and return specific structure class.
     *
     * @param messageText Message text from Telegram bot.
     * @return Specific structure class
     */
    public Optional<BotCommandCustom> parseBotCommandCustom(
            final String messageText) {
        return botCommandsSet.stream()
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
            final BotCommandCustom botCommandCustom, final Update update) {
        botCommandCustom.doAction(this, update);
    }

    /**
     * Process received message.
     *
     * @param update Update instance that has been gotten from Telegram user.
     */
    public void processReceivedMessage(final Update update) {

        final long chatId = update.getMessage().getChatId();
        final String messageText = update.getMessage().getText();

        final Optional<BotCommandCustom> botCommandCustomOptional
                = parseBotCommandCustom(messageText);

        // Parse text message from Telegram User
        botCommandCustomOptional.ifPresentOrElse(_
                        -> acceptBotCommandCustom(
                        botCommandCustomOptional.get(), update),
                () -> sendMessage(chatId,
                        "Головне в нашому житті - не тупікувати!!!"));

    }


    @SneakyThrows
    @Retryable(retryFor = TelegramApiException.class, maxAttempts = 2,
            backoff = @Backoff(delay = Constant.RETRYABLE_BACKOFF_DELAY))
    public void processReceivedCommand(final Update update) {
        // direct case
        onUpdateReceivedDirect(update);
        // contains case
        onUpdateReceivedContains(update);
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

        if (businessUtility.isTelegramBotAdmin(chatId)) {
            sendMessage(chatId, "123");
            sendMessage(chatId, callbackQuery);
        } else {

        }
//
//
//        if (callbackQuery.equals("YES_BUTTON")) {
//            final String text = "You pressed YES button.";
//            editMessage(StaticUtility.buildEditMessageText(
//                    chatId, text, messageId));
//        } else if (callbackQuery.equals("NO_BUTTON")) {
//            final String text = "You pressed NO button.";
//            editMessage(StaticUtility.buildEditMessageText(
//                    chatId, text, messageId));
//        }

    }


    /**
     * Process Direct case.
     *
     * @param update Update instance.
     */
    void onUpdateReceivedDirect(final Update update) {

        Optional<BotCommandCustom> firstEqual = botCommandsSet.stream()
                .filter(botCommandCustom
                        -> update.getMessage().getText()
                        .equals(botCommandCustom.getMessageText()))
                .findFirst();

        if (firstEqual.isPresent()) {
            // Text message
            processReceivedMessage(update);
        }

    }

    /**
     * Process Contains case.
     *
     * @param update Update instance.
     */
    void onUpdateReceivedContains(final Update update) {

        Optional<BotCommandCustom> first = botCommandsSet.stream()
                .filter(botCommandCustom
                        -> update.getMessage().getText().length()
                        > botCommandCustom.getMessageText().length() + 1)
                .filter(botCommandCustom
                        -> update.getMessage().getText()
                        .startsWith(botCommandCustom.getMessageText()))
                .findFirst();

        first.ifPresent(botCommandCustom
                -> botCommandCustom.doAction(this, update));

    }

    @Override
    public void onUpdateReceived(final Update update) {


        log.info("Received update: {}", update);

//        if (businessUtility.isTelegramBotAdmin(update.getMessage().getChatId())) {
//
//            // todo Caching list of user roles
//            Optional<UserRole> byId = userRoleDao.findById(2L);
//
//            botCommandsSet = botCommandsMap.get(byId.get());
//        } else {
//
//            Optional<UserRole> byId = userRoleDao.findById(1L);
//            botCommandsSet = botCommandsMap.get(byId.get());
//        }

        addPreparedBotCommandsToBot();

        // Parse received message from Telegram User
        if (update.hasMessage() && update.getMessage().hasText()) {
            if (update.getMessage().getText().equals(SEND_NEXT_DATE_FACT)) {
                sendMessage(update.getMessage().getChatId(), "123"); // todo
                dateFactService.sendNextDateFact(sendMessageOperation);
            } else if (update.getMessage().getText().equals(SEND_AD)) {
                sendMessage(update.getMessage().getChatId(), "123"); // todo
                adService.sendNextAd(sendMessageOperation);
            } else {
                // Command message
                processReceivedCommand(update);
            }
        } else if (update.hasCallbackQuery()) {
            // Callback message (for example user click on button)
            processReceivedCallback(update);
        }
    }

    @Autowired
    DateFactService dateFactService;
    @Autowired
    AdService adService;


    @Getter
    Operation sendMessageOperation = this::sendMessage;

}
