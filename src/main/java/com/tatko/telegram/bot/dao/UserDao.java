package com.tatko.telegram.bot.dao;

import java.util.List;
import java.util.Optional;

import com.tatko.telegram.bot.entity.User;
import com.tatko.telegram.bot.repository.UserRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     *
     * @param chatId Identifier for Telegram Chat.
     * @return {@code Optional<User>} of {@code Optional.empty()}
     * if the user is not found.
     */
    public Optional<User> findByChatId(final Long chatId) {
        return userRepository.findByChatId(chatId);
    }

    /**
     * Save User entity into DB.
     *
     * @param user entity
     * @return Saved User
     */
    public User save(final User user) {
        return userRepository.save(user);
    }

    /**
     * Return list of all users.
     *
     * @return List of users.
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

}
