package com.tatko.telegram.bot.dao;

import com.tatko.telegram.bot.entity.UserRole;
import com.tatko.telegram.bot.repository.UserRoleRepository;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Setter
@Slf4j
public class UserRoleDao {

    /**
     * Autowired by Spring UserRoleRepository bean.
     */
    @Autowired
    private UserRoleRepository userRoleRepository;

    /**
     * todo have to be cashed
     * Get list of all UserRole entities.
     * @return List of all UserRole entities.
     */
    public List<UserRole> findAll() {
        log.debug("Finding all roles");
        List<UserRole> userRoleList = userRoleRepository.findAll();
        log.debug("Found {} roles", userRoleList.size());
        return userRoleList;
    }

    /**
     * Get UserRole entity by its Id.
     * @param id Id.
     * @return UserRole entity.
     */
    @Cacheable("findUserRoleById")
    public Optional<UserRole> findById(final Long id) {
        log.debug("Finding role with id {}", id);
        Optional<UserRole> userRoleOptional = userRoleRepository.findById(id);
        log.debug("Found role: {}", userRoleOptional);
        return userRoleOptional;
    }

}
