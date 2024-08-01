package com.tatko.telegram.bot.service.custom.command;

import com.tatko.telegram.bot.BusinessUtility;
import com.tatko.telegram.bot.dao.UserDao;
import com.tatko.telegram.bot.service.TelegramBot;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Service
public class BotCommandCustomSendBroadcastAction extends BotCommandCustom {

    /**
     * BusinessUtility instance.
     */
    @Autowired
    private BusinessUtility businessUtility;

    /**
     * UserDao instance.
     */
    @Autowired
    private UserDao userDao;

    /**
     * Constructor.
     */
    public BotCommandCustomSendBroadcastAction() {
        super(Action.SEND,
                "/send", "send to all users");
    }

    /**
     * Do action for this action.
     *
     * @param telegramBot Telegram Bot instance.
     * @param update      Received update from Telegram user.
     */
    @Override
    public void doAction(final TelegramBot telegramBot, final Update update) {
        if (businessUtility.isTelegramBotAdmin(
                update.getMessage().getChatId())) {
            // Broadcast
            var textToSend
                    = EmojiParser.parseToUnicode(update.getMessage().getText()
                    .substring(update.getMessage().getText().indexOf(" ")));

            var users = userDao.findAll();
            for (var user : users) {
                log.debug("Sending user: {}", user);
                telegramBot.sendMessage(user.getChatId(), textToSend);
            }
        } else {
            telegramBot.sendMessage(update.getMessage().getChatId(),
                    "You are not the owner of this chat.");
        }

    }

}
