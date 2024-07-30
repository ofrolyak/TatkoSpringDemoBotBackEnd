package com.tatko.telegram.bot.service;

import com.tatko.telegram.bot.config.TelegramBotConfig;
import com.tatko.telegram.bot.dao.UserDao;
import com.tatko.telegram.bot.entity.User;
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
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
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

        // Parse text message from Telegram User
        botCommandCustomOptional.ifPresentOrElse(_
                        -> acceptBotCommandCustom(
                        botCommandCustomOptional.get(), update),
                () -> sendMessage(chatId,
                        "Головне в нашому житті - не тупікувати!!!"));

    }

    /**
     * Create EditMessageText instance.
     *
     * @param chatId ChatId.
     * @param text Text message.
     * @param messageId Telegram message identifier.
     *
     * @return Created EditMessageText instance.
     */
    EditMessageText buildEditMessageText(
            final long chatId, final String text, final int messageId) {
        return EditMessageText.builder()
                .chatId(chatId)
                .text(text)
                .messageId(messageId)
                .build();
    }

    /**
     * Process received callback.
     *
     * @param update Update instance that has been gotten from Telegram user.
     */
    @SneakyThrows
    @Retryable(retryFor = TelegramApiException.class, maxAttempts = 2,
            backoff = @Backoff(delay = RETRYABLE_BACKOFF_DELAY))
    public void processReceivedCallback(final Update update) {

        final String callbackQuery = update.getCallbackQuery().getData();
        final int messageId = update.getCallbackQuery().getMessage().getMessageId();
        final long chatId = update.getCallbackQuery().getMessage().getChatId();

        if (callbackQuery.equals("YES_BUTTON")) {
            final String text = "You pressed YES button.";
            execute(buildEditMessageText(chatId, text, messageId));
        } else if (callbackQuery.equals("NO_BUTTON")) {
            final String text = "You pressed NO button.";
            execute(buildEditMessageText(chatId, text, messageId));
        }

    }

    /**
     * Sth.
     *
     * @param update Update received
     */
    @Override
    public void onUpdateReceived(final Update update) {

        log.info("Received update: {}", update);

        // Parse received message from Telegram User
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Text message
            processReceivedMessage(update);
        } else if (update.hasCallbackQuery()) {
            // Callback message (for example user click on button)
            processReceivedCallback(update);
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
        final long chatId = update.getMessage().getChatId();
        sendMessage(chatId, "This is bot for demonstration how to Spring Boot"
                + " works with Telegram.",
                KeyboardMarkupHolder.replyKeyboardMarkupInstance);

    }

    /**
     * Process no action.
     *
     * @param update Prepared update instance for Telegram chat.
     */
    public void processNoAction(final Update update) {
        // NOP
    }

    /**
     * Create SendMessage instance.
     *
     * @param chatId Identifier for Telegram Chat.
     * @param text Message text.
     * @param replyKeyboard Keyboard.
     *
     * @return SendMessage instance.
     */
    SendMessage buildSendMessage(final long chatId, final String text,
                                 final ReplyKeyboard replyKeyboard) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(replyKeyboard)
                .build();
    }

    /**
     * Process register action.
     *
     * @param update Prepared update instance for Telegram chat.
     */
    @SneakyThrows
    @Retryable(retryFor = TelegramApiException.class, maxAttempts = 2,
            backoff = @Backoff(delay = RETRYABLE_BACKOFF_DELAY))
    public void processRegisterAction(final Update update) {

        final long chatId = update.getMessage().getChat().getId();

        execute(buildSendMessage(chatId, "Do you really want to register?",
                KeyboardMarkupHolder.inlineKeyboardMarkup));

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
        botCommandSet.add(new BotCommandCustom(Action.REGISTER,
                "/register", "register user",
                this::processRegisterAction));
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
     * @param name   Name for Telegram user.
     */
    public void startCommandReceived(final long chatId, final String name) {

        log.info("Starting command received: {}", name);

        final String message = "Hi " + name + " from " + chatId
                + "! Nice to meet you!!!" + "\uD83C\uDF4C";

        sendMessage(chatId, message);
    }

    /**
     * Send message without keyboard.
     *
     * @param chatId  Identifier for Telegram chat.
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
     * @param chatId              Identifier for Telegram chat.
     * @param message             Prepared message instance for Telegram chat.
     * @param replyKeyboardMarkup ReplyKeyboardMarkup instance.
     */
    @SneakyThrows
    @Retryable(retryFor = TelegramApiException.class, maxAttempts = 2,
            backoff = @Backoff(delay = RETRYABLE_BACKOFF_DELAY))
    public void sendMessage(final long chatId, final String message,
                            final ReplyKeyboardMarkup replyKeyboardMarkup) {

        log.info("Sending message: {}", message);

        final SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        execute(sendMessage);

    }

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
     * Get Bot Token.
     *
     * @return Bot Token
     */
    @Override
    public String getBotToken() {
        return telegramBotConfig.getTelegramBotToken();
    }

}
