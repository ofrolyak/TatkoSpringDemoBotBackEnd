package com.tatko.telegram.bot;

import com.tatko.telegram.bot.config.TelegramBotConfig;
import com.tatko.telegram.bot.dao.BotCommandDao;
import com.tatko.telegram.bot.dao.BotCommandMapDao;
import com.tatko.telegram.bot.dao.UserRoleDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BusinessUtility {

    /**
     * TelegramBotConfig.
     */
    @Autowired
    private TelegramBotConfig telegramBotConfig;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    UserRoleDao userRoleDao;

    @Autowired
    BotCommandDao botCommandDao;

    @Autowired
    BotCommandMapDao botCommandMapDao;

    /**
     * Verify if chatId is Admin.
     *
     * @param chatId chatId for Telegram user.
     * @return If chatId is Admin.
     */
    //@OnUpdateReceivedBeforeProcessorAnnotation
    public boolean isTelegramBotAdmin(final Long chatId) {
        return chatId == telegramBotConfig.getTelegramBotOwner();
    }

//    private BotCommandCustom getBeanByBotCommandMap(BotCommandMap botCommandMap) {
//
//        String[] beanNamesForType = applicationContext.getBeanNamesForType(BotCommandCustom.class);
//
//        Optional<BotCommandCustom> first = Arrays.stream(beanNamesForType)
//                .map(beanName -> applicationContext.getBean(beanName, BotCommandCustom.class))
//                .filter(botCommandCustom -> botCommandCustom.getId() == botCommandMap.getBotCommandId())
//                .findFirst();
//
//        BotCommandCustom botCommandCustom = first.orElseThrow();
//
//        return botCommandCustom;
//
//    }

//    private Set<BotCommandCustom> getBotCommandCustomListByUserRole(UserRole userRole) {
//
//        final Set<BotCommandCustom> botCommandSetForUser = new HashSet<>();
//
//        List<BotCommandMap> botCommandMapsList = botCommandMapDao.findAllByUserRoleId(userRole.getId());
//
//        botCommandMapsList.forEach(botCommandMap -> botCommandSetForUser.add(getBeanByBotCommandMap(botCommandMap)));
//
//        return botCommandSetForUser;
//    }


//    /**
//     * Build BotCommandCustom.
//     *
//     * @return Set of BotCommandCustom
//     */
//    public Map<UserRole, Set<BotCommandCustom>> buildBotCommandsMap() {
//
//        Map<UserRole, Set<BotCommandCustom>> botCommandsMap = new HashMap<>();
//
//        List<UserRole> userRolesList = userRoleDao.findAll();
//
//        userRolesList.forEach(userRole -> botCommandsMap.put(userRole, getBotCommandCustomListByUserRole(userRole)));
//
//        return botCommandsMap;
//
//    }

}
