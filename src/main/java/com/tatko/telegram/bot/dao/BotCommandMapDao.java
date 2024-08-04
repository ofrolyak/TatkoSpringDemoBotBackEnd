package com.tatko.telegram.bot.dao;

import com.tatko.telegram.bot.entity.BotCommandMap;
import com.tatko.telegram.bot.repository.BotCommandMapRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Setter
public class BotCommandMapDao {

    @Autowired
    private BotCommandMapRepository botCommandMapRepository;

    public List<BotCommandMap> findAllByUserRoleId(Long userRoleId) {
        return botCommandMapRepository.findAllByUserRoleId(userRoleId);
    }

}
