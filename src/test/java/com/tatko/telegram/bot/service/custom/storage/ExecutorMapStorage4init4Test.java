package com.tatko.telegram.bot.service.custom.storage;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.assertj.core.api.Assertions.assertThat;

class ExecutorMapStorage4init4Test extends MockitoExtensionBaseMockTests {

    @InjectMocks
    private ExecutorMapStorage executorMapStorage;

    @Test
    void before4Test() {

        // Then
        assertThat(executorMapStorage.getExecutorMap())
                .isEmpty();

    }

    @Test
    void postConstruct4successCase4Test() {

//        // Before
//        UserRole userRole1 = getGen().nextUserRole();
//        UserRole userRole2 = getGen().nextUserRole();
//
//        // When
//        when(userRoleDao.findById(eq(1L)))
//                .thenReturn(Optional.of(userRole1));
//        when(userRoleDao.findById(eq(2L)))
//                .thenReturn(Optional.of(userRole2));

        // Action
        executorMapStorage.init();

        // Then
        assertThat(executorMapStorage.getExecutorMap())
                .hasSize(4);

    }

}