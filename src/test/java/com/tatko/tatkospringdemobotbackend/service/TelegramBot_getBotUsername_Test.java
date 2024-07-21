package com.tatko.tatkospringdemobotbackend.service;

import com.tatko.tatkospringdemobotbackend.BaseMockTests;
import com.tatko.tatkospringdemobotbackend.config.TelegramBotConfig;
import com.tatko.tatkospringdemobotbackend.dao.UserDao;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class TelegramBot_getBotUsername_Test extends BaseMockTests {

    @Mock
    TelegramBotConfig telegramBotConfig;
    @MockBean
    UserDao userDao;
    @InjectMocks
    TelegramBot telegramBot;

    @Test
    void getBotUsername_Test() {

        // Before
        String telegramBotName = gen.nextString();

        // When
        when(telegramBotConfig.getTelegramBotName())
                .thenReturn(telegramBotName);

        // Then
        assertThat(telegramBot.getBotUsername())
                .isEqualTo(telegramBotName);

    }
}