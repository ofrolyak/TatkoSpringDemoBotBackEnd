package com.tatko.tatkospringdemobotbackend;

import com.tatko.tatkospringdemobotbackend.service.TelegramBot;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class TelegramBotInitializer_init_Test extends BaseMockTests {

    @Mock
    TelegramBot telegramBot;
    @Spy
    @InjectMocks
    TelegramBotInitializer telegramBotInitializer;

    @SneakyThrows
    @Test
    void init_Test() {

        // When
        doNothing()
                .when(telegramBotInitializer)
                .createRegisterBot();
        doNothing()
                .when(telegramBot)
                .addCommandToBot();

        // Then
        assertThatCode(() -> telegramBotInitializer.init())
                .doesNotThrowAnyException();
        verify(telegramBot, times(1))
                .addCommandToBot();

    }

    @SneakyThrows
    @Test
    void init_TelegramApiException_Test() {

        // When
        lenient()
                .doThrow(TelegramApiException.class)
                .when(telegramBotInitializer)
                .createRegisterBot();
        lenient()
                .doNothing()
                .when(telegramBot)
                .addCommandToBot();

        // Then
        assertThatThrownBy(() -> telegramBotInitializer.init())
                .isInstanceOf(TelegramApiException.class);
        verify(telegramBot, never())
                .addCommandToBot();

    }

}