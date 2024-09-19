package com.tatko.telegram.bot.dao;

import com.tatko.telegram.bot.SpringBootTestFullBaseMockTests;
import com.tatko.telegram.bot.entity.UserRole;
import com.tatko.telegram.bot.repository.UserRoleRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.anyLong;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRoleDao4findById4Test extends SpringBootTestFullBaseMockTests {

    @MockBean
    UserRoleRepository userRoleRepository;
    @Autowired
    UserRoleDao userRoleDao;

    @Test
    void cache4Test() {

        // Before
        UserRole userRole = getGen().nextUserRole();
        userRoleRepository.save(userRole);

        // Action
        userRoleDao.findById(userRole.getId());
        userRoleDao.findById(userRole.getId());
        userRoleDao.findById(userRole.getId());

        // Then
        Mockito.verify(userRoleRepository, Mockito.times(1))
                .findById(anyLong());
    }

}