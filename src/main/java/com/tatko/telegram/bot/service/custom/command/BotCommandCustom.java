package com.tatko.telegram.bot.service.custom.command;

import com.tatko.telegram.bot.service.TelegramBot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;
/**
 * Service information for specific action.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BotCommandCustom {

    /**
     * Action.
     */
    private Action action;

    /**
     * Message text that referring for this action.
     */
    private String messageText;

    /**
     * Description.
     */
    private String description;

    /**
     * Do some action after receive update from Telegram user.
     *
     * @param telegramBot Telegram Bot instance.
     * @param update Received update from Telegram user.
     */
    public abstract void doAction(TelegramBot telegramBot, Update update);

}
