package com.tatko.tatkospringdemobotbackend.service;

import com.tatko.tatkospringdemobotbackend.config.TelegramBotConfig;
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
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    @Autowired
    TelegramBotConfig telegramBotConfig;

    @PostConstruct
    public void postConstructInit(){

        addCommandToBot();
    }

    @Override
    public void onUpdateReceived(Update update) {

        log.info("Received update: {}", update);

        if (update.hasMessage() && update.getMessage().hasText()) {

            long chatId = update.getMessage().getChatId();
            String firstName = update.getMessage().getChat().getFirstName();
            String messageText = update.getMessage().getText();

            switch (messageText) {
                case "/start":
                    startCommandReceived(chatId, firstName);
                    break;
                case "/help":
                    sendMessage(chatId, "This is bot for demonstration how to Spring Boot works with Telegram.");
                    break;
                default:
                    sendMessage(chatId, "Головне в нашому житті - не тупікувати!!!");
                    break;
            }
        }

    }

    @SneakyThrows
    void addCommandToBot() {
        List<BotCommand> botCommandList = new ArrayList<>();
        botCommandList.add(new BotCommand("/start", "get welcome message"));
        botCommandList.add(new BotCommand("/getmydata", "get your data"));
        botCommandList.add(new BotCommand("/deletemydata", "delete your data"));
        botCommandList.add(new BotCommand("/help", "how to use this bot"));
        botCommandList.add(new BotCommand("/settings", "settings and preferences"));
        this.execute(new SetMyCommands(botCommandList, new BotCommandScopeDefault(), null));
    }

    void startCommandReceived(long chatId, String name) {

        log.info("Starting command received: {}", name);

        String message = "Hi " + name + " from " + chatId + "! Nice to meet you!!!";

        sendMessage(chatId, message);
    }

    @SneakyThrows
    @Retryable(retryFor = TelegramApiException.class, maxAttempts = 2, backoff = @Backoff(delay = 100))
    void sendMessage(long chatId, String message) {

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
