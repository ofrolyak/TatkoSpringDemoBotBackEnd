package com.tatko.telegram.bot.util;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.entity.User;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

class BusinessUtility4buildUserByMessage4Test extends MockitoExtensionBaseMockTests {

    @Spy
    @InjectMocks
    BusinessUtility businessUtility;

    @Test
    void success4isAdminCase4Test() {

        // Before
        Message message = getGen().nextMessage();

       // When
//       doReturn(true)
//               .when(businessUtility)
//                       .isTelegramBotAdmin(eq(chatId));

        // Action
        User user = businessUtility.buildUserByMessage(message);

        // Then
        assertThat(user)
                .isNotNull();
        assertThat(user.getChatId())
                .isEqualTo(message.getChatId());
        assertThat(user.getFirstName())
                .isEqualTo(message.getChat().getFirstName());
        assertThat(user.getLastName())
                .isEqualTo(message.getChat().getLastName());
        assertThat(user.getUserName())
                .isEqualTo(message.getChat().getUserName());
//        assertThat(user.getUserRole().getId())
//                .isEqualTo(2L);

    }

    @Test
    void success4isNotAdminCase4Test() {

        // Before
        Message message = getGen().nextMessage();

        // When
//        doReturn(false)
//                .when(businessUtility)
//                .isTelegramBotAdmin(eq(chatId));

        // Action
        User user = businessUtility.buildUserByMessage(message);

        // Then
        assertThat(user)
                .isNotNull();
        assertThat(user.getChatId())
                .isEqualTo(message.getChatId());
        assertThat(user.getFirstName())
                .isEqualTo(message.getChat().getFirstName());
        assertThat(user.getLastName())
                .isEqualTo(message.getChat().getLastName());
        assertThat(user.getUserName())
                .isEqualTo(message.getChat().getUserName());
//        assertThat(user.getUserRole().getId())
//                .isEqualTo(1L);

    }

}
