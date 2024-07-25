package com.tatko.tatkospringdemobotbackend.repository;

import com.tatko.tatkospringdemobotbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for User entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find User.
     * @param chatId Identifier for Telegram Chat
     * @return Optional<User> of Optional.empty() if the user is not found
     */
    Optional<User> findByChatId(Long chatId);

}
