package com.tatko.telegram.bot.dao;

import com.tatko.telegram.bot.entity.User;
import com.tatko.telegram.bot.repository.UserRepository;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Object for User entity.
 */
@Service
@Setter
@Slf4j
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
        log.debug("Finding user by chatId: {}", chatId);
        Optional<User> userOptional = userRepository.findByChatId(chatId);
        log.debug("Found userOptional: {}", userOptional);
        return userOptional;
    }

    /**
     * Save User entity into DB.
     *
     * @param user entity
     * @return Saved User
     */
    public User save(final User user) {
        log.debug("Saving user: {}", user);
        User userSaved = userRepository.save(user);
        log.debug("Saved userSaved: {}", userSaved);
        return userSaved;
    }

    /**
     * Return list of all users.
     *
     * @return List of users.
     */
    public List<User> findAll() {
        log.debug("Finding all users");
        List<User> users = userRepository.findAll();
        log.debug("Found users: {}", users);
        return users;
    }

    /**
     * Delete User entity.
     * @param user User entity.
     */
    public void delete(final User user) {
        log.debug("Deleting user: {}", user);
        userRepository.delete(user);
        log.debug("Deleted user: {}", user);
    }

    /**
     * Find User entity by user id.
     * @param id User id
     * @return Optional of User entity
     */
    public Optional<User> findById(final long id) {
        log.debug("Finding user by id: {}", id);
        Optional<User> userOptional = userRepository.findById(id);
        log.debug("Found userOptional: {}", userOptional);
        return userOptional;
    }

}
