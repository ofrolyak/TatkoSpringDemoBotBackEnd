package com.tatko.telegram.bot.util;

import com.tatko.telegram.bot.config.TelegramBotConfig;
import com.tatko.telegram.bot.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.LocalDateTime;

@Slf4j
@Service
public class BusinessUtility {

    /**
     * TelegramBotConfig.
     */
    @Autowired
    private TelegramBotConfig telegramBotConfig;

    /**
     * Verify if chatId is Admin.
     *
     * @param chatId chatId for Telegram user.
     * @return If chatId is Admin.
     */
    public boolean isTelegramBotAdmin(final Long chatId) {
        log.debug("Verify if chatId={} is a telegram bot admin", chatId);
        boolean isTelegramBotAdmin
                = chatId.equals(telegramBotConfig.getTelegramBotOwner());
        log.debug("chatId={}, isTelegramBotAdmin={}",
                chatId, isTelegramBotAdmin);
        return isTelegramBotAdmin;
    }

    /**
     * Build User instance based on Message instance.
     *
     * @param message
     * @return User instance.
     */
    public User buildUserByMessage(final Message message) {
        log.debug("Process buildUserByMessage for {}", message);
        User user = User.builder()
                .chatId(message.getChatId())
                .firstName(message.getChat().getFirstName())
                .lastName(message.getChat().getLastName())
                .userName(message.getChat().getUserName())
                .registeredAt(LocalDateTime.now())
                //.userRoleId(isTelegramBotAdmin(message.getChatId()) ? 2L : 1L)
                .build();
        log.debug("Process buildUserByMessage for {}, user={}", message, user);
        return user;
    }

}
