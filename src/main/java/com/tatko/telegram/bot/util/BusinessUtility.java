package com.tatko.telegram.bot.util;

import com.tatko.telegram.bot.config.TelegramBotConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return chatId.equals(telegramBotConfig.getTelegramBotOwner());
    }

}
