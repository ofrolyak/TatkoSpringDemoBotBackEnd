package com.tatko.telegram.bot.service.custom.storage;

import com.tatko.telegram.bot.service.TelegramBotService;
import com.tatko.telegram.bot.service.custom.operation.OperationMarkerInterface;
import com.tatko.telegram.bot.service.custom.operation.SendMessageOperation1Param;
import com.tatko.telegram.bot.service.custom.operation.SendMessageOperation2Params;
import com.tatko.telegram.bot.service.custom.operation.SendMessageOperation3Params;
import com.tatko.telegram.bot.service.custom.operation.SetBotCommandsListOperation;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ExecutorMapStorage {

    /**
     * Bean.
     */
    @Autowired
    private TelegramBotService telegramBotService;

    /**
     * Structure.
     */
    @Getter
    private final Map<Class<? extends OperationMarkerInterface>,
            OperationMarkerInterface> executorMap = new HashMap<>();

    /**
     * Operation/wrapper for Telegram Bot execute method.
     */
    @Getter
    private final SendMessageOperation2Params
            sendMessageOperation2ParamsOperation
            = new SendMessageOperation2Params() {
        @Override
        public void execute(final long a, final String b) {
            telegramBotService.sendMessage(a, b);
        }
    };

    /**
     * Operation/wrapper for Telegram Bot execute method.
     */
    @Getter
    private final SendMessageOperation1Param
            sendMessageSendMessage1ParamOperation
            = new SendMessageOperation1Param() {
        @Override
        public void execute(final SendMessage a) {
            telegramBotService.sendMessage(a);
        }
    };

    /**
     * Operation/wrapper for Telegram Bot execute method.
     */
    @Getter
    private final SendMessageOperation3Params
            sendMessageSendMessage3ParamsOperation
            = new SendMessageOperation3Params() {
        @Override
        public void execute(final long a, final String b,
                            final ReplyKeyboardMarkup c) {
            telegramBotService.sendMessage(a, b, c);
        }
    };

    /**
     * Operation/wrapper for Telegram Bot execute method.
     */
    @Getter
    private final SetBotCommandsListOperation
            setBotCommandsListOperation = new SetBotCommandsListOperation() {
        @Override
        public void setBotCommandsList(final List<BotCommand> botCommandList) {
            telegramBotService.setBotCommandsList(botCommandList);
        }
    };

    /**
     * Init.
     */
    @PostConstruct
    public void init() {

        executorMap.put(SendMessageOperation2Params.class,
                sendMessageOperation2ParamsOperation);
        executorMap.put(SendMessageOperation1Param.class,
                sendMessageSendMessage1ParamOperation);
        executorMap.put(SendMessageOperation3Params.class,
                sendMessageSendMessage3ParamsOperation);
        executorMap.put(SetBotCommandsListOperation.class,
                setBotCommandsListOperation);

    }

}
