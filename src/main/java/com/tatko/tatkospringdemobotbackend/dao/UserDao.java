package com.tatko.tatkospringdemobotbackend.dao;

import com.tatko.tatkospringdemobotbackend.entity.User;
import com.tatko.tatkospringdemobotbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDao {

    @Autowired
    UserRepository userRepository;

    public Optional<User> findByChatId(Long chatId) {
        return userRepository.findByChatId(chatId);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

}
