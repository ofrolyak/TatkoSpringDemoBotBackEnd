package com.tatko.telegram.bot;

//import com.tatko.tatkospringdemobotbackend.dao.UserDao;
//import com.tatko.tatkospringdemobotbackend.service.TelegramBot;
//import lombok.SneakyThrows;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Spy;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.boot.test.mock.mockito.MockBeans;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import static org.assertj.core.api.Assertions.assertThatCode;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.lenient;
//import static org.mockito.Mockito.never;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
/**
 * JUnit test for TelegramBotInitializer class init method.
 */
class TelegramBotInitializer4init4Test extends BaseMockTests {

//    @MockBean
//    UserDao userDao;
//    @MockBean
//    TelegramBot telegramBot;
//    @Spy
//    @InjectMocks
//    //@Autowired
//    TelegramBotInitializer telegramBotInitializer;
//
//    @SneakyThrows
//    @Test
//    void init_Test() {
//
//        // When
//        doNothing()
//                .when(telegramBotInitializer)
//                .createRegisterBot();
//        doNothing()
//                .when(telegramBot)
//                .addPreparedBotCommandsToBot();
//
//        // Then
//        assertThatCode(() -> telegramBotInitializer.init())
//                .doesNotThrowAnyException();
//        verify(telegramBot, times(1))
//                .addPreparedBotCommandsToBot();
//
//    }
//
//    @SneakyThrows
//    @Test
//    void init_TelegramApiException_Test() {
//
//        // When
//        lenient()
//                .doThrow(TelegramApiException.class)
//                .when(telegramBotInitializer)
//                .createRegisterBot();
//        lenient()
//                .doNothing()
//                .when(telegramBot)
//                .addPreparedBotCommandsToBot();
//
//        // Then
//        assertThatThrownBy(() -> telegramBotInitializer.init())
//                .isInstanceOf(TelegramApiException.class);
//        verify(telegramBot, never())
//                .addPreparedBotCommandsToBot();
//
//    }

}
