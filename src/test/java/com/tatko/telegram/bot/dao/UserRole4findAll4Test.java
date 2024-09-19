package com.tatko.telegram.bot.dao;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.entity.UserRole;
import com.tatko.telegram.bot.repository.UserRoleRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserRole4findAll4Test extends MockitoExtensionBaseMockTests {

    @Mock
    UserRoleRepository userRoleRepository;
    @InjectMocks
    UserRoleDao userRoleDao;

    @Test
    void save4success4notExist4Test() {

        // When
        Mockito.when(userRoleRepository.findAll())
                .thenReturn(Collections.emptyList());

        // Action
        Collection<UserRole> userRoleCollection = userRoleDao.findAll();

        // Then
        assertThat(userRoleCollection)
                .isEmpty();
        Mockito.verify(userRoleRepository, Mockito.times(1))
                .findAll();
    }

    @Test
    void save4success4exist4Test() {

        // Before
        UserRole userRole = getGen().nextUserRole();

        // When
        Mockito.when(userRoleRepository.findAll())
                .thenReturn(List.of(userRole));

        // Action
        List<UserRole> userRoleCollection = userRoleDao.findAll();

        // Then
        assertThat(userRoleCollection)
                .isNotEmpty();
        assertThat(userRoleCollection)
                .hasSize(1);
        assertThat(userRoleCollection.getFirst())
                .isEqualTo(userRole);
        Mockito.verify(userRoleRepository, Mockito.times(1))
                .findAll();
    }

}