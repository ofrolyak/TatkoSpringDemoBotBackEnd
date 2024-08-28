package com.tatko.telegram.bot.service.custom.storage;

import com.tatko.telegram.bot.dao.UserRoleDao;
import com.tatko.telegram.bot.entity.UserRole;
import com.tatko.telegram.bot.exception.UserRoleNotFoundException;
import com.tatko.telegram.bot.service.custom.button.KeyButton;
import com.tatko.telegram.bot.service.custom.command.BotCommandCustom;
import com.tatko.telegram.bot.service.custom.command.BotCommandCustomServiceAction;
import com.tatko.telegram.bot.service.custom.command.BotCommandCustomSettingsAction;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.tatko.telegram.bot.service.custom.button.KeyButton.DELETE_MY_DATA;
import static com.tatko.telegram.bot.service.custom.button.KeyButton.GET_MY_DATA;
import static com.tatko.telegram.bot.service.custom.button.KeyButton.SEND_AD;
import static com.tatko.telegram.bot.service.custom.button.KeyButton.SEND_NEXT_DATE_FACT;

@Getter
@Component
public class KeyButtonMapStorage {

    /**
     * Autowired by Spring UserRoleDao bean.
     */
    @Autowired
    private UserRoleDao userRoleDao;

    /**
     * Date structure.
     */
    private final Map<UserRole, Map<Class<? extends BotCommandCustom>,
            Set<KeyButton>>> keyButtonMap = new HashMap<>();

    /**
     * Building date structure.
     */
    @PostConstruct
    public void init() {
        keyButtonMap.putAll(Map.of(userRoleDao.findById(2L)
                        .orElseThrow(UserRoleNotFoundException::new),
                Map.of(BotCommandCustomServiceAction.class,
                        Set.of(SEND_NEXT_DATE_FACT, SEND_AD)),
                userRoleDao.findById(1L)
                        .orElseThrow(UserRoleNotFoundException::new),
                Map.of(BotCommandCustomServiceAction.class,
                        Set.of(),
                        BotCommandCustomSettingsAction.class,
                        Set.of(GET_MY_DATA, DELETE_MY_DATA))));
    }

}
