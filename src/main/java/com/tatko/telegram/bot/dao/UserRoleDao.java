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

    @Autowired
    private UserRoleRepository userRoleRepository;

    public List<UserRole> findAll() {
        return userRoleRepository.findAll();
    }

    public Optional<UserRole> findById(Long id) {
        return userRoleRepository.findById(id);
    }

}
