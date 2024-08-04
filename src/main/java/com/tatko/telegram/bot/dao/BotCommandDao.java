package com.tatko.telegram.bot.dao;

import com.tatko.telegram.bot.entity.BotCommand;
import com.tatko.telegram.bot.repository.BotCommandRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Setter
public class BotCommandDao {

    @Autowired
    private BotCommandRepository botCommandRepository;

    public Optional<BotCommand> findById(Long id) {
        return botCommandRepository.findById(id);
    }

}
