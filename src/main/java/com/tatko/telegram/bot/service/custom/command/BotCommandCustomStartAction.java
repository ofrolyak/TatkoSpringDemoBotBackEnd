package com.tatko.telegram.bot.service.custom.command;

import com.tatko.telegram.bot.dao.UserDao;
import com.tatko.telegram.bot.entity.User;
import com.tatko.telegram.bot.service.TelegramBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDateTime;

@Slf4j
@Service
public class BotCommandCustomStartAction extends BotCommandCustom {

    /**
     * Constructor.
     */
    @Autowired
    private UserDao userDao;

    /**
     * Constructor.
     */
    public BotCommandCustomStartAction() {
        super(Action.START, "/start", "get welcome message");
    }

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
     * React on START action.
     *
     * @param telegramBot Telegram Bot instance.
     * @param chatId Chat identifier in Telegram Bot.
     * @param name   Name for Telegram user.
     */
    public void startCommandReceived(
            final TelegramBot telegramBot, final long chatId,
            final String name) {

        log.info("Starting command received: {}", name);

        final String message = "Hi " + name + " from " + chatId
                + "! Nice to meet you!!!" + "\uD83C\uDF4C";

        telegramBot.sendMessage(chatId, message);
    }

    /**
     * Do action for this action.
     *
     * @param telegramBot Telegram Bot instance.
     * @param update Received update from Telegram user.
     */
    @Override
    public void doAction(final TelegramBot telegramBot, final Update update) {
        final Message message = update.getMessage();
        final long chatId = update.getMessage().getChatId();
        final String firstName = update.getMessage().getChat().getFirstName();
        registerUser(message);
        startCommandReceived(telegramBot, chatId, firstName);
    }

}
