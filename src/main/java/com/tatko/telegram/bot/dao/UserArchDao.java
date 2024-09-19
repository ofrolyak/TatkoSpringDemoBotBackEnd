package com.tatko.telegram.bot.dao;

import com.tatko.telegram.bot.entity.UserArch;
import com.tatko.telegram.bot.repository.UserArchRepository;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Data Access Object for User entity.
 */
@Service
@Setter
@Slf4j
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
        log.debug("Saving userArch: {}", userArch);
        UserArch entitySaved = userArchRepository.save(userArch);
        log.debug("Saved entity entitySaved: {}", entitySaved);
        return entitySaved;
    }


}
