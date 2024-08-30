package com.tatko.telegram.bot.util;

import com.tatko.telegram.bot.entity.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public class StaticUtility {

    /**
     * DUMMY for HideUtilityClassConstructor by CheckStyle.
     */
    @SuppressWarnings("unused")
    public final void foo() {
        throw new UnsupportedOperationException();
    }

    /**
     * Create SendMessage instance.
     *
     * @param chatId chatId.
     * @param text   Message text.
     * @return SendMessage instance.
     */
    public static SendMessage buildSendMessage(
            final long chatId, final String text) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build();
    }

    /**
     * Create SendMessage instance.
     *
     * @param chatId        Identifier for Telegram Chat.
     * @param text          Message text.
     * @param replyKeyboard Keyboard.
     * @return SendMessage instance.
     */
    // todo Double code - refactor it
    public static SendMessage buildSendMessage(
            final long chatId, final String text,
            final ReplyKeyboard replyKeyboard) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(replyKeyboard)
                .build();
    }


    /**
     * Create EditMessageText instance.
     *
     * @param chatId    ChatId.
     * @param text      Text message.
     * @param messageId Telegram message identifier.
     * @return Created EditMessageText instance.
     */
    public static EditMessageText buildEditMessageText(
            final long chatId, final String text, final int messageId) {
        return EditMessageText.builder()
                .chatId(chatId)
                .text(text)
                .messageId(messageId)
                .build();
    }

    /**
     * Build SendMessage instance.
     *
     * @param textMessage
     * @param user
     * @return SendMessage instance.
     */
    public static SendMessage buildSendMessage(
            final String textMessage, final User user) {
        return SendMessage.builder()
                .text(textMessage)
                .chatId(user.getChatId())
                .build();
    }

    /**
     * Read and define chatId based on Update instance.
     *
     * @param update
     * @return chatId value.
     */
    public static long readChatId(final Update update) {

        long chatId;

        if (update.hasMessage()
                && update.getMessage().hasText()) {
            chatId = update.getMessage().getChatId();
        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
        } else {
            throw new IllegalArgumentException();
        }

        return chatId;
    }

}
