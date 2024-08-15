package com.tatko.telegram.bot.dao;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.entity.Ad;
import com.tatko.telegram.bot.repository.AdRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;

class AdDao4findAdToDeliver4Test extends MockitoExtensionBaseMockTests {

    @Mock
    AdRepository adRepository;
    @InjectMocks
    AdDao adDao;

    @Test
    void findAdToDeliver4successAndExist4Test() {

        // Before
        Ad ad = getGen().nextObject(Ad.class);
        LocalDateTime localDateTime = getGen().nextObject(LocalDateTime.class);

        // When
        Mockito.when(
                adRepository
                        .findFirstByDeliveredTimeIsNullOrDeliveredTimeIsBefore(
                eq(localDateTime)))
                .thenReturn(Optional.of(ad));

        // Action
        Optional<Ad> adOptional = adDao.findAdToDeliver(localDateTime);

        // Then
        assertThat(adOptional.isPresent())
                .isTrue();
        assertThat(adOptional.get())
                .isEqualTo(ad);
        Mockito.verify(adRepository, Mockito.times(1))
                .findFirstByDeliveredTimeIsNullOrDeliveredTimeIsBefore(
                        eq(localDateTime));
    }

    @Test
    void findAdToDeliver4successAndNotExist4Test() {

        // Before
        Ad ad = getGen().nextObject(Ad.class);
        LocalDateTime localDateTime = getGen().nextObject(LocalDateTime.class);

        // When
        Mockito.when(
                adRepository
                        .findFirstByDeliveredTimeIsNullOrDeliveredTimeIsBefore(
                eq(localDateTime)))
                .thenReturn(Optional.empty());

        // Action
        Optional<Ad> adOptional = adDao.findAdToDeliver(localDateTime);

        // Then
        assertThat(adOptional.isEmpty())
                .isTrue();
        Mockito.verify(adRepository, Mockito.times(1))
                .findFirstByDeliveredTimeIsNullOrDeliveredTimeIsBefore(
                        eq(localDateTime));
    }

}