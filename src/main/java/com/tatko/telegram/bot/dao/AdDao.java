package com.tatko.telegram.bot.dao;

import com.tatko.telegram.bot.entity.Ad;
import com.tatko.telegram.bot.repository.AdRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class AdDao {

    @Autowired
    private AdRepository adRepository;

    public Ad save(Ad ad) {
        return adRepository.save(ad);
    }

    public Optional<Ad> findAdToDeliver(LocalDateTime localDateTime) {
        log.debug("findAdToDeliver");
        Optional<Ad> adOptional = adRepository.findFirstByDeliveredTimeIsNullOrDeliveredTimeIsBefore(localDateTime);
        log.debug("adOptional: {}", adOptional);
        return adOptional;
    }

}
