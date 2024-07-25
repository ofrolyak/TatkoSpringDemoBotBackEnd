package com.tatko.tatkospringdemobotbackend.dao;

import com.tatko.tatkospringdemobotbackend.entity.User;
import com.tatko.tatkospringdemobotbackend.repository.UserRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Data Access Object for User entity.
 */
@Service
@Setter
public class UserDao {

    /**
     * UserRepository is used to get User based info.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Find User.
     * @param chatId Identifier for Telegram Chat.
     * @return {@code Optional<User>} of {@code Optional.empty()}
     * if the user is not found.
     */
    public Optional<User> findByChatId(final Long chatId) {
        return userRepository.findByChatId(chatId);
    }

    /**
     * Save User entity into DB.
     * @param user entity
     * @return Saved User
     */
    public User save(final User user) {
        return userRepository.save(user);
    }

}
