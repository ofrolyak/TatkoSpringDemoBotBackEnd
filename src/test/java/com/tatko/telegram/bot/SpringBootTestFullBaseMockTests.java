package com.tatko.telegram.bot;

import com.tatko.telegram.bot.config.TelegramBotConfig;
import com.tatko.telegram.bot.service.TelegramBotConfiguratorService;
import com.tatko.telegram.bot.service.custom.storage.KeyButtonMapStorage;
import com.tatko.telegram.bot.service.processor.TelegramBotProcessorService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

/**
 * Parent class for JUnit classes that is build by Mockito.
 */
@SpringBootTest
public class SpringBootTestFullBaseMockTests extends BaseMockTests {

    @MockBean
    KeyButtonMapStorage keyButtonMapStorage;
    @MockBean
    TelegramBotProcessorService telegramBotProcessorService;
    @MockBean
    TelegramBotConfiguratorService telegramBotConfiguratorService;
    @MockBean
    TelegramBotConfig telegramBotConfig;

}
