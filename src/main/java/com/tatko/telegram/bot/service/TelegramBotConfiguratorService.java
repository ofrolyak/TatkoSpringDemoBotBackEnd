package com.tatko.telegram.bot.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.tatko.telegram.bot.service.custom.storage.ServiceDataUserStorage;
import com.tatko.telegram.bot.service.internal.UserService;
import com.tatko.telegram.bot.util.BusinessUtility;
import com.tatko.telegram.bot.dao.UserDao;
import com.tatko.telegram.bot.dao.UserRoleDao;
import com.tatko.telegram.bot.entity.User;
import com.tatko.telegram.bot.entity.UserRole;
import com.tatko.telegram.bot.exception.UserNotFoundException;
import com.tatko.telegram.bot.exception.UserRoleNotFoundException;
import com.tatko.telegram.bot.service.custom.storage.BotCommandCustomSetStorage;
import com.tatko.telegram.bot.service.custom.operation.OperationMarkerInterface;
import com.tatko.telegram.bot.service.custom.operation.SetBotCommandsListOperation;
import com.tatko.telegram.bot.service.custom.storage.ExecutorMapStorage;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

@Slf4j
@Service
public class TelegramBotConfiguratorService {

    /**
     * Autowired by Spring ExecutorMapStorage bean.
     */
    @Autowired
    private ExecutorMapStorage executorMapStorage;

    /**
     * Autowired by Spring BusinessUtility bean.
     */
    @Autowired
    private BusinessUtility businessUtility;

    /**
     * Autowired by Spring UserDao bean.
     */
    @Autowired
    private UserDao userDao;

    /**
     * Autowired by Spring UserRoleDao bean.
     */
    @Autowired
    private UserRoleDao userRoleDao;

    /**
     * Autowired by Spring UserService bean.
     */
    @Autowired
    private UserService userService;

    /**
     * ThreadLocal holder for structure for specific user activity.
     */
    @Getter
    private final ThreadLocal<ServiceDataUserStorage> serviceDataUserThreadLocal
            = new ThreadLocal<>();


    /**
     * Add prepared bot commands to Telegram bot.
     */
    @SneakyThrows
    public void addPreparedBotCommandsToBot() {

        SetBotCommandsListOperation setBotCommandsListOperation
                = getOperationByClass(executorMapStorage.getExecutorMap(),
                SetBotCommandsListOperation.class);

        addBotCommandListToBot(botCommandCustomSetStorage.getBotCommandList(),
                setBotCommandsListOperation);
    }

    /**
     * Autowired by Spring BotCommandCustomSetStorage bean.
     */
    @Autowired
    private BotCommandCustomSetStorage botCommandCustomSetStorage;


    /**
     * Add prepared bot commands to Telegram bot.
     *
     * @param botCommandList
     * @param setBotCommandsListOperation
     */
    @SneakyThrows
    public void addBotCommandListToBot(
            final List<BotCommand> botCommandList,
            final SetBotCommandsListOperation setBotCommandsListOperation) {

        setBotCommandsListOperation.setBotCommandsList(botCommandList);
    }

    private <T> T getOperationByClass(
            final Map<Class<? extends OperationMarkerInterface>, ? extends
                    OperationMarkerInterface> operationMarkerInterface,
            final Class<T> clazz) {

        T operationMarkerInterface2 = (T) operationMarkerInterface.get(clazz);

        if (Objects.isNull(operationMarkerInterface2)) {
            throw new IllegalArgumentException();
        }

        return operationMarkerInterface2;
    }

    /**
     * Get specific OperationMarkerInterface instance
     * by OperationMarkerInterface class.
     *
     * @param clazz
     * @param <T>
     * @return Specific OperationMarkerInterface instance.
     */
    public <T> T getOperationByClass(final Class<T> clazz) {
        return getOperationByClass(executorMapStorage.getExecutorMap(), clazz);
    }

    /**
     * Based on update create/refresh structure for current thread/user.
     *
     * @param update
     */
    public void configureServiceData(final Update update) {

        long chatId;

        if (update.hasMessage() && update.getMessage().hasText()) {
            chatId = update.getMessage().getChatId();
        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
        } else {
            throw new IllegalArgumentException();
        }

        ServiceDataUserStorage serviceDataUserStorage
                = new ServiceDataUserStorage();
        serviceDataUserStorage.setBroken(false);

        final User user;
        User userRegistered;

        try {
            userRegistered = userDao.findByChatId(chatId)
                    .orElseThrow(UserNotFoundException::new);
        } catch (UserNotFoundException e) {
            try {
                userService.registerUser(update.getMessage());
                userRegistered = userDao.findByChatId(
                                update.getMessage().getChatId())
                        .orElseThrow(UserNotFoundException::new);
                this.configureServiceData(update);
            } catch (Exception e1) {
                serviceDataUserStorage.setBroken(true);
                return;
            }
        }

        user = userRegistered;

        serviceDataUserStorage.setAdmin(
                businessUtility.isTelegramBotAdmin(chatId));
        serviceDataUserStorage.setUser(user);

        UserRole userRoleCurrent = userRoleDao.findAll().stream()
                .filter(userRole
                        -> userRole.getId().equals(user.getUserRoleId()))
                .findFirst()
                .orElseThrow(UserRoleNotFoundException::new);

        serviceDataUserStorage.setUserRole(userRoleCurrent);

        serviceDataUserThreadLocal.set(serviceDataUserStorage);

    }

}
