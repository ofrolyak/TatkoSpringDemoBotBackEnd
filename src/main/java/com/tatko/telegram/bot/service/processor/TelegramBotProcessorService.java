package com.tatko.telegram.bot.service.processor;

import com.tatko.telegram.bot.audit.OnUpdateReceivedBeforeProcessorAnnotation;
import com.tatko.telegram.bot.service.TelegramBotConfiguratorService;
import com.tatko.telegram.bot.service.custom.operation.SendMessageOperation2Params;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Service
public class TelegramBotProcessorService {

    /**
     * Autowired by Spring TextMessageProcessorService bean.
     */
    @Autowired
    private TextMessageProcessorService textMessageProcessorService;

    /**
     * Autowired by Spring TelegramBotConfiguratorService bean.
     */
    @Autowired
    private TelegramBotConfiguratorService telegramBotConfiguratorService;

    /**
     * Autowired by Spring CallbackProcessorService bean.
     */
    @Autowired
    private CallbackProcessorService callbackProcessorService;

    /**
     * Enter point for Telegram Bot activity.
     * @param update
     */
    @OnUpdateReceivedBeforeProcessorAnnotation
    public void onUpdateReceived(final Update update) {

        log.info("Received update: {}", update);

        // Parse received message from Telegram User
        if (update.hasMessage()
                && update.getMessage().hasText()) {
            textMessageProcessorService.processReceivedTextMessage(update,
                    telegramBotConfiguratorService.getOperationByClass(
                            SendMessageOperation2Params.class));
        } else if (update.hasCallbackQuery()) {
            // Callback message (for example user click on button)
            callbackProcessorService.processReceivedCallback(update);
        } else {
            throw new IllegalArgumentException();
        }

        log.info("Finished process received update: {}", update);

    }

}
