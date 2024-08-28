package com.tatko.telegram.bot.dao;

import com.tatko.telegram.bot.entity.UserRole;
import com.tatko.telegram.bot.repository.UserRoleRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Setter
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
        return userRoleRepository.findAll();
    }

    /**
     * Get UserRole entity by its Id.
     * @param id Id.
     * @return UserRole entity.
     */
    public Optional<UserRole> findById(final Long id) {
        return userRoleRepository.findById(id);
    }

}
