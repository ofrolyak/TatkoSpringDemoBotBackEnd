package com.tatko.telegram.bot.service;

import com.tatko.telegram.bot.dao.UserDao;
import com.tatko.telegram.bot.dao.UserRoleDao;
import com.tatko.telegram.bot.entity.User;
import com.tatko.telegram.bot.service.custom.operation.OperationMarkerInterface;
import com.tatko.telegram.bot.service.custom.operation.SetBotCommandsListOperation;
import com.tatko.telegram.bot.service.custom.storage.BotCommandCustomSetStorage;
import com.tatko.telegram.bot.service.custom.storage.ExecutorMapStorage;
import com.tatko.telegram.bot.service.custom.storage.ServiceDataUserStorage;
import com.tatko.telegram.bot.service.internal.UserRoleService;
import com.tatko.telegram.bot.service.internal.UserService;
import com.tatko.telegram.bot.util.BusinessUtility;
import com.tatko.telegram.bot.util.StaticUtility;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public final class TelegramBotConfiguratorService {

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
     * Autowired by Spring BotCommandCustomSetStorage bean.
     */
    @Autowired
    private BotCommandCustomSetStorage botCommandCustomSetStorage;

    /**
     * Autowired by Spring UserRoleService bean.
     */
    @Autowired
    private UserRoleService userRoleService;

    /**
     * ThreadLocal holder for structure for specific user activity.
     */
    @Getter
    private final ThreadLocal<ServiceDataUserStorage> serviceDataUserThreadLocal
            = new ThreadLocal<>();

    /**
     * getSetBotCommandsListOperation.
     *
     * @return SetBotCommandsListOperation instance.
     */
    SetBotCommandsListOperation getSetBotCommandsListOperation() {

        Map<Class<? extends OperationMarkerInterface>,
                OperationMarkerInterface> executorMap
                = executorMapStorage.getExecutorMap();

        SetBotCommandsListOperation setBotCommandsListOperation
                = getOperationByClass(executorMap,
                SetBotCommandsListOperation.class);

        return setBotCommandsListOperation;
    }

    /**
     * Add prepared bot commands to Telegram bot.
     */
    //@SneakyThrows
    public void addPreparedBotCommandsToBot() {

        SetBotCommandsListOperation setBotCommandsListOperation
                = getSetBotCommandsListOperation();

        List<BotCommand> botCommandList
                = botCommandCustomSetStorage.getBotCommandList();

        addBotCommandListToBot(botCommandList, setBotCommandsListOperation);
    }

    /**
     * Add prepared bot commands to Telegram bot.
     *
     * @param botCommandList
     * @param setBotCommandsListOperation
     */
    //@SneakyThrows
    public void addBotCommandListToBot(
            final List<BotCommand> botCommandList,
            final SetBotCommandsListOperation setBotCommandsListOperation) {

        setBotCommandsListOperation.setBotCommandsList(botCommandList);
    }

    /**
     * Get Operation By Class.
     *
     * @param operationMarkerInterface
     * @param clazz
     * @param <T>
     * @return Operation By Class.
     */
    <T> T getOperationByClass(
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

        ServiceDataUserStorage serviceDataUserStorage
                = new ServiceDataUserStorage();

        try {

            long chatId = StaticUtility.readChatId(update);
            serviceDataUserStorage.setChatId(chatId);

            final User user = userService.getRegisteredUser(update);
            serviceDataUserStorage.setUser(user);

            serviceDataUserStorage.setAdmin(
                    businessUtility.isTelegramBotAdmin(chatId));

            serviceDataUserStorage.setUserRole(
                    userRoleService.getUserRoleByUser(user));

        } catch (Exception e) {
            serviceDataUserStorage.setBroken(true);
        }

        serviceDataUserThreadLocal.set(serviceDataUserStorage);

    }

}
