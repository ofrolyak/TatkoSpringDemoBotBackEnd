package com.tatko.telegram.bot.service.custom.storage;

import com.tatko.telegram.bot.service.custom.command.BotCommandCustom;
import com.tatko.telegram.bot.service.custom.command.BotCommandCustomHelpAction;
import com.tatko.telegram.bot.service.custom.command.BotCommandCustomServiceAction;
import com.tatko.telegram.bot.service.custom.command.BotCommandCustomSettingsAction;
import com.tatko.telegram.bot.service.custom.command.BotCommandCustomStartAction;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Component
@Slf4j
public class BotCommandCustomSetStorage {

    /**
     * Business date structure.
     */
    private final Set<BotCommandCustom> botCommandCustomSet = new HashSet<>();

    /**
     * Autowired by Spring ApplicationContext bean.
     */
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Init.
     */
    @PostConstruct
    public void init() {

        log.info("Process init");

        botCommandCustomSet.add(applicationContext.getBean(
                BotCommandCustomStartAction.class));
        botCommandCustomSet.add(applicationContext.getBean(
                BotCommandCustomSettingsAction.class));
        botCommandCustomSet.add(applicationContext.getBean(
                BotCommandCustomServiceAction.class));
        botCommandCustomSet.add(applicationContext.getBean(
                BotCommandCustomHelpAction.class));

        log.info("Finished process init");

    }

    /**
     * Data structure accessor.
     * @return Data structure.
     */
    public List<BotCommand> getBotCommandList() {

        log.info("Process getBotCommandList");

        List<BotCommand> list = botCommandCustomSet.stream()
                .map(botCommandCustom
                        -> new BotCommand(botCommandCustom.getMessageText(),
                        botCommandCustom.getDescription()))
                .toList();

        log.info("Finished process getBotCommandList: {}", list);

        return list;
    }

}
