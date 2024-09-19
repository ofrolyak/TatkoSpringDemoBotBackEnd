package com.tatko.telegram.bot.service.custom.command;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.entity.UserRole;
import com.tatko.telegram.bot.service.TelegramBotConfiguratorService;
import com.tatko.telegram.bot.service.custom.storage.KeyButtonMapStorage;
import com.tatko.telegram.bot.service.custom.storage.ServiceDataUserStorage;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.tatko.telegram.bot.service.custom.button.KeyButton.SEND_AD;
import static com.tatko.telegram.bot.service.custom.button.KeyButton.SEND_NEXT_DATE_FACT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class BotCommandCustom4buildCollectionOfButtons4Test extends MockitoExtensionBaseMockTests {

    @Mock
    KeyButtonMapStorage keyButtonMapStorage;
    @Mock
    TelegramBotConfiguratorService telegramBotConfiguratorService;
    @InjectMocks
    BotCommandCustomServiceAction botCommandCustom;

    @Test
    void success4empty4Test() {

        // Before
        UserRole userRole = getGen().nextUserRole();
        ServiceDataUserStorage serviceDataUserStorage = getGen().nextServiceDataUserStorage();
        serviceDataUserStorage.setUserRole(userRole);


        // When
        when(keyButtonMapStorage.getKeyButtonMap())
                .thenReturn(Map.of(userRole,
                        Map.of(BotCommandCustomServiceAction.class,
                                Set.of(SEND_NEXT_DATE_FACT, SEND_AD))));
        when(telegramBotConfiguratorService.getServiceDataUserThreadLocal())
                .thenReturn(ThreadLocal.withInitial(() -> serviceDataUserStorage));

        // Action
        List<String> strings = botCommandCustom.buildCollectionOfButtons();

        // Then
        assertThat(strings)
                .isNotNull();
        assertThat(strings)
                .hasSize(2);

    }

}


