package com.tatko.tatkospringdemobotbackend.service;

import com.tatko.tatkospringdemobotbackend.config.TelegramBotConfig;
import com.tatko.tatkospringdemobotbackend.dao.UserDao;
import com.tatko.tatkospringdemobotbackend.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Telegram Bot service.
 */
@NoArgsConstructor
@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    /**
     * A canonical backoff period is equal to 100 milliseconds.
     */
    public static final int RETRYABLE_BACKOFF_DELAY = 100;

    /**
     * TelegramBotConfig itself .
     */
    @Autowired
    private TelegramBotConfig telegramBotConfig;

    /**
     * UserDao itself.
     */
    @Setter
    @Autowired
    private UserDao userDao;

    /**
     * Created and filled collection with BotCommandCustom instances.
     */
    @Getter
    private final Set<BotCommandCustom> botCommandsSet = buildBotCommandsMap();

    /**
     * Created and filled ReplyKeyboardMarkup instance.
     */
    private final ReplyKeyboardMarkup replyKeyboardMarkupInstance1
            = buildReplyKeyboardMarkup();

    /**
     * Register User. If he's already registered do nothing.
     *
     * @param message Message instance that has gotten from Telegram Bot.
     */
    public void registerUser(final Message message) {

        if (userDao.findByChatId(message.getChatId()).isEmpty()) {

            final User user = User.builder()
                    .chatId(message.getChatId())
                    .firstName(message.getChat().getFirstName())
                    .lastName(message.getChat().getLastName())
                    .userName(message.getChat().getUserName())
                    .registeredAt(LocalDateTime.now())
                    .build();

            userDao.save(user);
        }
    }

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
     * @param update Update instance from Telegram Bot.
     */
    public void acceptBotCommandCustom(final BotCommandCustom botCommandCustom,
                                       final Update update) {
        botCommandCustom.getConsumer().accept(update);
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

        botCommandCustomOptional.ifPresentOrElse(_
                        -> acceptBotCommandCustom(
                        botCommandCustomOptional.get(), update),
                () -> sendMessage(chatId,
                        "Головне в нашому житті - не тупікувати!!!"));

    }

    /**
     * Sth.
     *
     * @param update Update received
     */
    @Override
    public void onUpdateReceived(final Update update) {

        log.info("Received update: {}", update);

        if (update.hasMessage() && update.getMessage().hasText()) {
            processReceivedMessage(update);
        }

    }

    /**
     * Process START action.
     *
     * @param update Prepared update instance for Telegram chat.
     */
    public void processStartAction(final Update update) {
        final Message message = update.getMessage();
        final long chatId = update.getMessage().getChatId();
        final String firstName = update.getMessage().getChat().getFirstName();
        registerUser(message);
        startCommandReceived(chatId, firstName);
    }

    /**
     * Process HELP action.
     *
     * @param update Prepared update instance for Telegram chat.
     */
    public void processHelpAction(final Update update) {
        long chatId = update.getMessage().getChatId();
        sendMessage(chatId, "This is bot for demonstration how to Spring Boot"
                + " works with Telegram.", replyKeyboardMarkupInstance1);

    }

    /**
     * Process no action.
     *
     * @param update Prepared update instance for Telegram chat.
     */
    public void processNoAction(final Update update) {

    }

    /**
     * Build BotCommandCustom.
     *
     * @return Set of BotCommandCustom
     */
    private Set<BotCommandCustom> buildBotCommandsMap() {
        final Set<BotCommandCustom> botCommandSet = new HashSet<>();
        botCommandSet.add(new BotCommandCustom(Action.START,
                "/start", "get welcome message", this::processStartAction));
        botCommandSet.add(new BotCommandCustom(Action.GET_MY_DATA,
                "/getmydata", "get your data", this::processNoAction));
        botCommandSet.add(new BotCommandCustom(Action.DELETE_MY_DATA,
                "/deletemydata", "delete your data", this::processNoAction));
        botCommandSet.add(new BotCommandCustom(Action.HELP,
                "/help", "how to use this bot", this::processHelpAction));
        botCommandSet.add(new BotCommandCustom(Action.SETTINGS,
                "/settings", "settings and preferences",
                this::processNoAction));
        return botCommandSet;
    }

    /**
     * Build BotCommandList.
     *
     * @return List of BotCommand
     */
    public List<BotCommand> buildBotCommands() {
        return buildBotCommandsMap()
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
        execute(new SetMyCommands(botCommandList, new BotCommandScopeDefault(),
                null));
    }

    /**
     * React on START action.
     *
     * @param chatId Chat identifier in Telegram Bot.
     * @param name Name for Telegram user.
     */
    public void startCommandReceived(final long chatId, final String name) {

        log.info("Starting command received: {}", name);

        final String message = "Hi " + name + " from " + chatId
                + "! Nice to meet you!!!" + "\uD83C\uDF4C";

        sendMessage(chatId, message);
    }

    /**
     * Build ReplyKeyboardMarkup.
     *
     * @return ReplyKeyboardMarkup
     */
    public ReplyKeyboardMarkup buildReplyKeyboardMarkup() {

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
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
     * Send message without keyboard.
     *
     * @param chatId Identifier for Telegram chat.
     * @param message Prepared message instance for Telegram chat.
     */
    @SneakyThrows
    @Retryable(retryFor = TelegramApiException.class, maxAttempts = 2,
            backoff = @Backoff(delay = RETRYABLE_BACKOFF_DELAY))
    public void sendMessage(final long chatId, final String message) {

        log.info("Sending message: {}", message);

        final SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);

        execute(sendMessage);

    }

    /**
     * Send message with keyboard.
     *
     * @param chatId Identifier for Telegram chat.
     * @param message Prepared message instance for Telegram chat.
     * @param replyKeyboardMarkup
     */
    @SneakyThrows
    @Retryable(retryFor = TelegramApiException.class, maxAttempts = 2,
            backoff = @Backoff(delay = RETRYABLE_BACKOFF_DELAY))
    public void sendMessage(final long chatId, final String message,
                            final ReplyKeyboardMarkup replyKeyboardMarkup) {

        log.info("Sending message: {}", message);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        execute(sendMessage);

    }

    /**
     * Get Bot UserName.
     * @return Bot UserName
     */
    @Override
    public String getBotUsername() {
        return telegramBotConfig.getTelegramBotName();
    }

    /**
     * Get Bot Token.
     * @return Bot Token
     */
    @Override
    public String getBotToken() {
        return telegramBotConfig.getTelegramBotToken();
    }

}
