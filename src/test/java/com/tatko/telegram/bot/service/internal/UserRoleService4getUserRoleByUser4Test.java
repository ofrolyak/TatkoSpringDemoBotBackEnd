package com.tatko.telegram.bot.service.internal;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.dao.UserRoleDao;
import com.tatko.telegram.bot.entity.User;
import com.tatko.telegram.bot.entity.UserRole;
import com.tatko.telegram.bot.exception.UserRoleNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.doReturn;

class UserRoleService4getUserRoleByUser4Test extends MockitoExtensionBaseMockTests {

    @Mock
    private UserRoleDao userRoleDaoMock;
    @InjectMocks
    private UserRoleService userRoleServiceMock;

    @Test
    void process4Test() {

        // Before
        UserRole userRole = getGen().nextUserRole();
        User user = getGen().nextUser();
        user.setUserRole(userRole);

        // When
        doReturn(List.of(userRole))
                .when(userRoleDaoMock)
                        .findAll();

        // Action
        UserRole userRoleByUser = userRoleServiceMock.getUserRoleByUser(user);

        // Then
        assertThat(userRoleByUser)
                .isEqualTo(userRole);

    }

    @Test
    void process4UserRoleNotFoundException4Test() {

        // Before
        UserRole userRole = getGen().nextUserRole();
        User user = getGen().nextUser();
        user.setUserRole(userRole);

        // When
        doReturn(List.of())
                .when(userRoleDaoMock)
                .findAll();

        // Then
        assertThatCode(() -> userRoleServiceMock.getUserRoleByUser(user))
                .isInstanceOf(UserRoleNotFoundException.class);

    }

}
