package com.tatko.telegram.bot.repository;

import com.tatko.telegram.bot.entity.BotCommandMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BotCommandMapRepository extends JpaRepository<BotCommandMap, Long> {

    public List<BotCommandMap> findAllByUserRoleId(Long userRoleId);

}
