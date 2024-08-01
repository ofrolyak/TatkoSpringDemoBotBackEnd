package com.tatko.telegram.bot.repository;

import com.tatko.telegram.bot.entity.Ad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Repository for Ad entity.
 */

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {

    Optional<Ad> findFirstByDeliveredTimeIsNullOrDeliveredTimeIsBefore(LocalDateTime localDateTime);




}
