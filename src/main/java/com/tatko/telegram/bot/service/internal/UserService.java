package com.tatko.telegram.bot.service.internal;

import com.tatko.telegram.bot.dao.UserArchDao;
import com.tatko.telegram.bot.dao.UserDao;
import com.tatko.telegram.bot.entity.User;
import com.tatko.telegram.bot.entity.UserArch;
import com.tatko.telegram.bot.exception.BaseException;
import com.tatko.telegram.bot.exception.UserNotFoundException;
import com.tatko.telegram.bot.service.custom.operation.SendMessageOperation1Param;
import com.tatko.telegram.bot.service.custom.operation.SendMessageOperation2Params;
import com.tatko.telegram.bot.service.processor.CallbackProcessorService;
import com.tatko.telegram.bot.util.BusinessUtility;
import com.tatko.telegram.bot.util.StaticUtility;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@Slf4j
public class UserService {

    /**
     * Autowired by Spring UserDao bean.
     */
    @Autowired
    private UserDao userDao;

    /**
     * Autowired by Spring UserArchDao bean.
     */
    @Autowired
    private UserArchDao userArchDao;

    /**
     * Autowired by Spring BusinessUtility bean.
     */
    @Autowired
    private BusinessUtility businessUtility;

    /**
     * Deliver text message to specific user.
     *
     * @param sendMessageOperation2Params
     * @param textMessage
     * @param chatId
     */
    public void deliverToUser(
            final SendMessageOperation2Params sendMessageOperation2Params,
            @NotNull final String textMessage, @NotNull final long chatId) {

        log.debug("Delivering textMessage {} to chatId {}", textMessage,
                chatId);

        sendMessageOperation2Params.execute(chatId, textMessage);

        log.debug("textMessage {} has been delivered to chatId {}",
                textMessage, chatId);
    }

    /**
     * Deliver text message to specific user.
     *
     * @param sendMessageOperation2Params
     * @param textMessage
     * @param user
     */
    public void deliverToUser(
            final SendMessageOperation2Params sendMessageOperation2Params,
            @NotNull final String textMessage, @NotNull final User user) {

        log.debug("Delivering textMessage {} to user {}", textMessage, user);

        deliverToUser(sendMessageOperation2Params, textMessage,
                user.getChatId());

        log.debug("textMessage {} has been delivered to user {}",
                textMessage, user);
    }

    /**
     * Deliver specific SendMessage structure.
     *
     * @param sendMessageOperation1Param
     * @param sendMessage
     */
    public void deliverToUser(
            final SendMessageOperation1Param sendMessageOperation1Param,
            @NotNull final SendMessage sendMessage) {

        log.debug("Delivering sendMessage {}", sendMessage);

        sendMessageOperation1Param.execute(sendMessage);

        log.debug("sendMessage {} has been delivered", sendMessage);
    }

    /**
     * Deliver text message to all users.
     *
     * @param sendMessageOperation2Params
     * @param textMessage
     */
    public void deliverToUsers(
            final SendMessageOperation2Params sendMessageOperation2Params,
            @NotNull final String textMessage) {

        log.debug("Delivering textMessage {}", textMessage);

        var users = userDao.findAll();

        log.debug("Found {} users", users.size());

        users.forEach(user ->
                deliverToUser(sendMessageOperation2Params, textMessage, user));

        log.debug("Delivered textMessage {}", textMessage);
    }

    /**
     * Deliver.
     * @param sendMessageOperation1Param
     * @param textMessage
     * @param user
     */
    public void deliverMessageWithButtonToUser(
            final SendMessageOperation1Param sendMessageOperation1Param,
            final String textMessage, final User user) {

        log.debug("Process deliverMessageWithButtonToUser for user {}", user);

        SendMessage sendMessage
                = StaticUtility.buildSendMessage(textMessage, user);

        CallbackProcessorService.andButtonToSendMessage(sendMessage);

        deliverToUser(sendMessageOperation1Param, sendMessage);

        log.debug("Finished process deliverMessageWithButtonToUser for user {}",
                user);

    }

    /**
     * Deliver text message to all users.
     *
     * @param sendMessageOperation1Param
     * @param textMessage
     */
    public void deliverToUsers(
            final SendMessageOperation1Param sendMessageOperation1Param,
            @NotNull final String textMessage) {

        log.debug("Delivering textMessage {}", textMessage);

        var users = userDao.findAll();

        log.debug("Found {} users", users.size());

        users.forEach(user -> deliverMessageWithButtonToUser(
                sendMessageOperation1Param, textMessage, user));

        log.debug("Delivered textMessage {}", textMessage);
    }

    /**
     * Register User. If he's already registered do nothing.
     *
     * @param message Message instance that has gotten from Telegram Bot.
     */
    public void registerUser(final Message message) {

        log.debug("Process register user for message {}", message);

        if (userDao.findByChatId(message.getChatId()).isEmpty()) {

            User user = businessUtility.buildUserByMessage(message);
            log.info("Register user {}", user);
            userDao.save(user);
        }

        log.debug("Finished process register user for message {}", message);

    }

    /**
     * Delete user based on User instance.
     *
     * @param user
     */
    @Transactional
    public void deleteUser(final User user) {

        log.debug("Process delete user {}", user);

        // Verify if user exists
        findUserByUser(user);

        UserArch userArch = new UserArch();

        BeanUtils.copyProperties(user, userArch);

        userArchDao.save(userArch);

        userDao.delete(user);

        log.debug("Finished process delete user {}", user);

    }

    /**
     * Find User entity by User entity.
     *
     * @param user
     * @return User entity.
     */
    public User findUserByUser(final User user) {
        log.debug("Process findUserByUser {}", user);
        User user1 = userDao.findById(user.getId())
                .orElseThrow(UserNotFoundException::new);
        log.debug("Finished process findUserByUser {}", user);
        return user1;
    }

    /**
     * Get registered user or create if it has not registered yet.
     *
     * @param update
     * @return Registered User instance.
     */
    public User getRegisteredUser(final Update update) {

        log.debug("Process getRegisteredUser {}", update);

        User userRegistered;

        final long chatId = update.getMessage().getChatId();

        try {
            userRegistered = userDao.findByChatId(chatId)
                    .orElseThrow(UserNotFoundException::new);
            log.debug("User found userRegistered: {}", userRegistered);
        } catch (UserNotFoundException e) {
            try {
                registerUser(update.getMessage());
                userRegistered = userDao.findByChatId(chatId)
                        .orElseThrow(UserNotFoundException::new);
                log.debug("User found once more userRegistered: {}",
                        userRegistered);
            } catch (Exception e1) {
                log.error("Error: ", e1);
                throw new BaseException();
            }
        }

        log.debug("Finished process getRegisteredUser {}", update);

        return userRegistered;

    }

}
