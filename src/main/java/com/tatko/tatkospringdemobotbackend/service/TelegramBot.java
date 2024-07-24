package com.tatko.tatkospringdemobotbackend.service;

import com.tatko.tatkospringdemobotbackend.config.TelegramBotConfig;
import com.tatko.tatkospringdemobotbackend.dao.UserDao;
import com.tatko.tatkospringdemobotbackend.entity.User;
import lombok.Getter;
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

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    @Autowired
    TelegramBotConfig telegramBotConfig;
    @Autowired
    UserDao userDao;

    @Getter
    private final Set<BotCommandCustom> botCommandsSet = buildBotCommandsMap();

    private final ReplyKeyboardMarkup replyKeyboardMarkup = buildReplyKeyboardMarkup();

    public void registerUser(Message message) {

        if (userDao.findByChatId(message.getChatId()).isEmpty()) {

            User user = User.builder()
                    .chatId(message.getChatId())
                    .firstName(message.getChat().getFirstName())
                    .lastName(message.getChat().getLastName())
                    .userName(message.getChat().getUserName())
                    .registeredAt(LocalDateTime.now())
                    .build();

            userDao.save(user);
        }
    }

    public Optional<BotCommandCustom> parseBotCommandCustom(String messageText) {
        return botCommandsSet.stream()
                .filter(botCommand -> botCommand.getMessageText().equals(messageText))
                .findFirst();
    }

    public void acceptBotCommandCustom(BotCommandCustom botCommandCustom, Update update) {
        botCommandCustom.getConsumer().accept(update);
    }

    public void processReceivedMessage(Update update) {

        long chatId = update.getMessage().getChatId();
        String messageText = update.getMessage().getText();

        Optional<BotCommandCustom> botCommandCustomOptional = parseBotCommandCustom(messageText);

        botCommandCustomOptional.ifPresentOrElse(_ -> acceptBotCommandCustom(botCommandCustomOptional.get(), update),
                () -> sendMessage(chatId, "Головне в нашому житті - не тупікувати!!!"));

    }

    @Override
    public void onUpdateReceived(Update update) {

        log.info("Received update: {}", update);

        if (update.hasMessage() && update.getMessage().hasText())
            processReceivedMessage(update);

    }

    public void processStartAction(Update update) {
        Message message = update.getMessage();
        long chatId = update.getMessage().getChatId();
        String firstName = update.getMessage().getChat().getFirstName();
        registerUser(message);
        startCommandReceived(chatId, firstName);
    }

    public void processHelpAction(Update update) {
        long chatId = update.getMessage().getChatId();
        sendMessage(chatId, "This is bot for demonstration how to Spring Boot works with Telegram.", replyKeyboardMarkup);

    }

    public void processNoAction(Update update) {

    }

    private Set<BotCommandCustom> buildBotCommandsMap() {
        Set<BotCommandCustom> botCommandSet = new HashSet<>();
        botCommandSet.add(new BotCommandCustom(Action.START, "/start", "get welcome message", this::processStartAction));
        botCommandSet.add(new BotCommandCustom(Action.GET_MY_DATA, "/getmydata", "get your data", this::processNoAction));
        botCommandSet.add(new BotCommandCustom(Action.DELETE_MY_DATA, "/deletemydata", "delete your data", this::processNoAction));
        botCommandSet.add(new BotCommandCustom(Action.HELP, "/help", "how to use this bot", this::processHelpAction));
        botCommandSet.add(new BotCommandCustom(Action.SETTINGS, "/settings", "settings and preferences", this::processNoAction));
        return botCommandSet;
    }

    public List<BotCommand> buildBotCommands() {
        return buildBotCommandsMap()
                .stream()
                .map(botCommandCustom -> new BotCommand(botCommandCustom.getMessageText(), botCommandCustom.getDescription()))
                .toList();
    }

    @SneakyThrows
    public void addCommandToBot() {
        List<BotCommand> botCommandList = buildBotCommands();
        execute(new SetMyCommands(botCommandList, new BotCommandScopeDefault(), null));
    }

    public void startCommandReceived(long chatId, String name) {

        log.info("Starting command received: {}", name);

        String message = "Hi " + name + " from " + chatId + "! Nice to meet you!!!" + "\uD83C\uDF4C";

        sendMessage(chatId, message);
    }

    public ReplyKeyboardMarkup buildReplyKeyboardMarkup() {

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardRow1 = new KeyboardRow();
        keyboardRow1.add("weather");
        keyboardRow1.add("get random joke");
        keyboardRowList.add(keyboardRow1);

        KeyboardRow keyboardRow2 = new KeyboardRow();
        keyboardRow2.add("register");
        keyboardRow2.add("check my data");
        keyboardRow2.add("delete my data");
        keyboardRowList.add(keyboardRow2);

        replyKeyboardMarkup.setKeyboard(keyboardRowList);

        return replyKeyboardMarkup;
    }

    @SneakyThrows
    @Retryable(retryFor = TelegramApiException.class, maxAttempts = 2, backoff = @Backoff(delay = 100))
    public void sendMessage(long chatId, String message) {

        log.info("Sending message: {}", message);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);

        execute(sendMessage);

    }

    @SneakyThrows
    @Retryable(retryFor = TelegramApiException.class, maxAttempts = 2, backoff = @Backoff(delay = 100))
    public void sendMessage(long chatId, String message, ReplyKeyboardMarkup replyKeyboardMarkup) {

        log.info("Sending message: {}", message);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        execute(sendMessage);

    }


    @Override
    public String getBotUsername() {
        return telegramBotConfig.getTelegramBotName();
    }

    @Override
    public String getBotToken() {
        return telegramBotConfig.getTelegramBotToken();
    }
}
