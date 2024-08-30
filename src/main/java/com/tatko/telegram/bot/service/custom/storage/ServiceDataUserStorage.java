package com.tatko.telegram.bot.service.custom.storage;

import com.tatko.telegram.bot.entity.User;
import com.tatko.telegram.bot.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class ServiceDataUserStorage {

    /**
     * Property that describe if this structure has been formed correctly.
     */
    private boolean isBroken;

    /**
     * Is admin property for user that make activity in Telegram bot.
     */
    private boolean isAdmin;

    /**
     * User instance for user that make activity in Telegram bot.
     */
    private User user;

    /**
     * UserRole instance for user that make activity in Telegram bot.
     */
    private UserRole userRole;

    /**
     * ChatId for current user.
     */
    private long chatId;

    /**
     * Constructor itself.
     */
    public ServiceDataUserStorage() {
        this.isBroken = false;
    }

}
