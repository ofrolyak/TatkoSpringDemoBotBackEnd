package com.tatko.telegram.bot;

import com.tatko.telegram.bot.entity.User;
import com.tatko.telegram.bot.entity.UserArch;
import com.tatko.telegram.bot.entity.UserRole;
import com.tatko.telegram.bot.service.custom.storage.ServiceDataUserStorage;
import org.jeasy.random.EasyRandom;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDateTime;

/**
 * Expansion functionality for EasyRandom functionality.
 */

public class EasyRandomCustom extends EasyRandom {

    /**
     * Generate String object.
     *
     * @return Get generated random String object.
     */
    public String nextString() {
        return this.nextObject(String.class);
    }

    public UserRole nextUserRole() {
        UserRole userRole = new UserRole();
        userRole.setId(this.nextLong());
        userRole.setName(this.nextString());
        return userRole;
    }

    public ServiceDataUserStorage nextServiceDataUserStorage() {
        ServiceDataUserStorage serviceDataUserStorage = new ServiceDataUserStorage();
        serviceDataUserStorage.setUser(this.nextUser());
        serviceDataUserStorage.setUserRole(this.nextUserRole());
        serviceDataUserStorage.setBroken(this.nextBoolean());
        serviceDataUserStorage.setAdmin(this.nextBoolean());
        serviceDataUserStorage.setChatId(this.nextLong());
        return serviceDataUserStorage;
    }

    public User nextUser() {
        User user = new User();
        user.setId(this.nextLong());
        user.setFirstName(this.nextString());
        user.setLastName(this.nextString());
        user.setUserName(this.nextString());
        user.setChatId(this.nextLong());
        user.setRegisteredAt(this.nextObject(LocalDateTime.class));
        user.setUserRole(this.nextUserRole());
        return user;
    }

    public UserArch nextUserArch() {
        UserArch userArch = new UserArch();
        userArch.setId(this.nextLong());
        userArch.setFirstName(this.nextString());
        userArch.setLastName(this.nextString());
        userArch.setUserName(this.nextString());
        userArch.setChatId(this.nextLong());
        userArch.setRegisteredAt(this.nextObject(LocalDateTime.class));
        userArch.setUserRole(this.nextUserRole());
        return userArch;
    }

    public Update nextUpdate() {
        Update update = new Update();
        update.setMessage(this.nextMessage());
        return update;
    }

    public Message nextMessage() {
        Message message = new Message();
        Chat chat = new Chat();
        chat.setId(this.nextLong());
        chat.setFirstName(this.nextString());
        message.setChat(chat);
        message.setText(nextString());
        return message;
    }



}
