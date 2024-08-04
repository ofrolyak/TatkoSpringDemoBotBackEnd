package com.tatko.telegram.bot.service.custom.command;

import com.tatko.telegram.bot.service.TelegramBotService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
/**
 * Service information for specific action.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public abstract class BotCommandCustom {

    /**
     * Action.
     */
    private String name;

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
     * @param telegramBotService Telegram Bot instance.
     * @param update Received update from Telegram user.
     */
    public abstract void doAction(TelegramBotService telegramBotService, Update update);

}
