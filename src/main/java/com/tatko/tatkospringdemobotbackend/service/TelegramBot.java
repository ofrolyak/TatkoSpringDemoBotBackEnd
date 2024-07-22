package com.tatko.tatkospringdemobotbackend.service;

import com.tatko.tatkospringdemobotbackend.config.TelegramBotConfig;
import com.tatko.tatkospringdemobotbackend.dao.UserDao;
import com.tatko.tatkospringdemobotbackend.entity.User;
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
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
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

    private final Set<BotCommandCustom> botCommandsSet = buildBotCommandsMap();

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

    public void processReceivedSpecificMessage(Update update, Action action) {

        botCommandsSet.stream()
                .filter(botCommandCustom -> botCommandCustom.getAction().equals(action))
                .map(BotCommandCustom::getConsumer)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .accept(update);
    }

    public void processReceivedMessage(Update update) {

        long chatId = update.getMessage().getChatId();
        String messageText = update.getMessage().getText();

        Optional<Action> actionOptional = botCommandsSet.stream()
                .filter(botCommand -> botCommand.getMessageText().equals(messageText))
                .map(BotCommandCustom::getAction)
                .findFirst();

        actionOptional.ifPresentOrElse(_ -> processReceivedSpecificMessage(update, actionOptional.get()), () -> sendMessage(chatId, "Головне в нашому житті - не тупікувати!!!"));

    }

    @Override
    public void onUpdateReceived(Update update) {

        log.info("Received update: {}", update);

        if (update.hasMessage() && update.getMessage().hasText())
            processReceivedMessage(update);

    }

    private Set<BotCommandCustom> buildBotCommandsMap() {
        Set<BotCommandCustom> botCommandSet = new HashSet<>();
        botCommandSet.add(new BotCommandCustom(Action.START, "/start", "get welcome message", update -> {
            Message message = update.getMessage();
            long chatId = update.getMessage().getChatId();
            String firstName = update.getMessage().getChat().getFirstName();
            registerUser(message);
            startCommandReceived(chatId, firstName);
        }));
        botCommandSet.add(new BotCommandCustom(Action.GET_MY_DATA, "/getmydata", "get your data", update -> {}));
        botCommandSet.add(new BotCommandCustom(Action.DELETE_MY_DATA, "/deletemydata", "delete your data", update -> {}));
        botCommandSet.add(new BotCommandCustom(Action.HELP, "/help", "how to use this bot", update -> {
            long chatId = update.getMessage().getChatId();
            sendMessage(chatId, "This is bot for demonstration how to Spring Boot works with Telegram.");
        }));
        botCommandSet.add(new BotCommandCustom(Action.SETTINGS, "/settings", "settings and preferences", update -> {}));
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

        String message = "Hi " + name + " from " + chatId + "! Nice to meet you!!!";

        sendMessage(chatId, message);
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


    @Override
    public String getBotUsername() {
        return telegramBotConfig.getTelegramBotName();
    }

    @Override
    public String getBotToken() {
        return telegramBotConfig.getTelegramBotToken();
    }
}
