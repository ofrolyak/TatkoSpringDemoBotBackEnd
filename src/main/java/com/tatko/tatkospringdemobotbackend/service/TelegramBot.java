package com.tatko.tatkospringdemobotbackend.service;

import com.tatko.tatkospringdemobotbackend.config.TelegramBotConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    @Autowired
    TelegramBotConfig telegramBotConfig;

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {

            long chatId = update.getMessage().getChatId();
            String firstName = update.getMessage().getChat().getFirstName();
            String messageText = update.getMessage().getText();

            switch (messageText) {
                case "/start":
                    startCommandReceived(chatId, firstName);
                    break;
                default:
                    //sendMessage(chatId, "Sorry, command was not recognized!!!");
                    sendMessage(chatId, "Головне в нашому житті - не тупікувати!!!");

            }
        }

    }

    void startCommandReceived(long chatId, String name) {
        String message = "Hi " + name + " from " + chatId + "!" +
                " Nice to meet you!!!";

        sendMessage(chatId, message);
    }

    void sendMessage(long chatId, String message) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }


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
