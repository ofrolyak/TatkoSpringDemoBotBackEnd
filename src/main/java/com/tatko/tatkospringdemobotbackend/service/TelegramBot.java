package com.tatko.tatkospringdemobotbackend.service;

import com.tatko.tatkospringdemobotbackend.config.TelegramBotConfig;
import com.tatko.tatkospringdemobotbackend.dao.UserDao;
import com.tatko.tatkospringdemobotbackend.entity.User;
import jakarta.annotation.PostConstruct;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    @Autowired
    TelegramBotConfig telegramBotConfig;
    @Autowired
    UserDao userDao;

//    @PostConstruct
//    public void postConstructInit(){
//
//        addCommandToBot();
//    }

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

    @Override
    public void onUpdateReceived(Update update) {

        log.info("Received update: {}", update);

        if (update.hasMessage() && update.getMessage().hasText()) {

            long chatId = update.getMessage().getChatId();
            String firstName = update.getMessage().getChat().getFirstName();
            String messageText = update.getMessage().getText();


            Optional<Action> actionOptional = Action.fromString(messageText);

            if (actionOptional.isEmpty())
                sendMessage(chatId, "Головне в нашому житті - не тупікувати!!!");
            else {
                switch (actionOptional.get()) {
                    case START:
                        registerUser(update.getMessage());
                        startCommandReceived(chatId, firstName);
                        break;
                    case HELP:
                        sendMessage(chatId, "This is bot for demonstration how to Spring Boot works with Telegram.");
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
            }



        }

    }

    @SneakyThrows
    public void addCommandToBot() {
        List<BotCommand> botCommandList = new ArrayList<>();
        botCommandList.add(new BotCommand("/start", "get welcome message"));
        botCommandList.add(new BotCommand("/getmydata", "get your data"));
        botCommandList.add(new BotCommand("/deletemydata", "delete your data"));
        botCommandList.add(new BotCommand("/help", "how to use this bot"));
        botCommandList.add(new BotCommand("/settings", "settings and preferences"));
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
