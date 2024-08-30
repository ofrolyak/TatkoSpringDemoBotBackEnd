package com.tatko.telegram.bot.service.internal;

import com.tatko.telegram.bot.dao.UserRoleDao;
import com.tatko.telegram.bot.entity.User;
import com.tatko.telegram.bot.entity.UserRole;
import com.tatko.telegram.bot.exception.UserRoleNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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

        return userRoleDao.findAll().stream()
                .filter(userRole
                        -> userRole.getId().equals(user.getUserRoleId()))
                .findFirst()
                .orElseThrow(UserRoleNotFoundException::new);

    }


}
