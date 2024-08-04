package com.tatko.telegram.bot.service;

import com.tatko.telegram.bot.dao.UserDao;
import com.tatko.telegram.bot.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

//    static int performOperation(int a, int b, Operation operation) {
//        return operation.execute(a, b);
//    }
//
//
//
//    public static void main(String[] args) {
//        int actualResult = performOperation(5, 3, new Operation() {
//            @Override
//            public int execute(int a, int b) {
//                return a + b;
//            }
//        });
//
//        System.out.println(actualResult);
//    }


    @Autowired
    UserDao userDao;

    void deliverToUser(Operation operation, @NotNull final String textMessage, @NotNull final User user) {

        log.debug("Delivering textMessage {} to user {}", textMessage, user);

        operation.execute(user.getChatId(), textMessage);

        log.debug("textMessage {} has been delivered to user {}", textMessage, user);
    }

    void deliverToUsers(Operation operation, @NotNull final String textMessage) {

        log.debug("Delivering textMessage {}", textMessage);

        var users = userDao.findAll();

        log.debug("Found {} users", users.size());

        users.forEach(user -> deliverToUser(operation, textMessage, user));

        log.debug("Delivered textMessage {}", textMessage);
    }
}
