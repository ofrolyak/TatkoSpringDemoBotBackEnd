package com.tatko.telegram.bot.dao;

import com.tatko.telegram.bot.entity.UserArch;
import com.tatko.telegram.bot.repository.UserArchRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Data Access Object for User entity.
 */
@Service
@Setter
public class UserArchDao {

    /**
     * UserRepository is used to get User based info.
     */
    @Autowired
    private UserArchRepository userArchRepository;

    /**
     * Save User entity into DB.
     *
     * @param userArch entity
     * @return Saved User
     */
    public UserArch save(final UserArch userArch) {
        return userArchRepository.save(userArch);
    }


}
