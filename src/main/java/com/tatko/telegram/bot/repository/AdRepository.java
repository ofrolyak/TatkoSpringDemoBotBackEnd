package com.tatko.telegram.bot.repository;

import com.tatko.telegram.bot.entity.Ad;
import com.tatko.telegram.bot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for Ad entity.
 */

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {


}
