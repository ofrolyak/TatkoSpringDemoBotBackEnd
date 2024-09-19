package com.tatko.telegram.bot.service.internal;

import com.tatko.telegram.bot.dao.UserRoleDao;
import com.tatko.telegram.bot.entity.User;
import com.tatko.telegram.bot.entity.UserRole;
import com.tatko.telegram.bot.exception.UserRoleNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserRoleService {

    /**
     * Autowired by Spring UserRoleDao instance.
     */
    @Autowired
    private UserRoleDao userRoleDao;

    /**
     * Get UserRole instance by User.
     *
     * @param user
     * @return UserRole instance.
     */
    public UserRole getUserRoleByUser(final User user) {

        log.debug("Process getUserRoleByUser for {}", user);

        UserRole userRole1 = userRoleDao.findAll().stream()
                .filter(userRole
                        -> userRole.getId().equals(user.getUserRole().getId()))
                .findFirst()
                .orElseThrow(UserRoleNotFoundException::new);

        log.debug("Finished process getUserRoleByUser for {}, userRole1: {}",
                user, userRole1);

        return userRole1;

    }


}
