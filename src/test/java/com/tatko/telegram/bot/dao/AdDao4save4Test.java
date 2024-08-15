package com.tatko.telegram.bot.dao;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.entity.Ad;
import com.tatko.telegram.bot.repository.AdRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

class AdDao4save4Test extends MockitoExtensionBaseMockTests {

    @Mock
    AdRepository adRepository;
    @InjectMocks
    AdDao adDao;

    @Test
    void save4success4Test() {

        // Before
        Ad ad = getGen().nextObject(Ad.class);

        // When
        Mockito.when(adRepository.save(Mockito.eq(ad)))
                .thenReturn(ad);

        // Action
        Ad adSaved = adDao.save(ad);

        // Then
        assertThat(adSaved)
                .isEqualTo(ad);
        Mockito.verify(adRepository, Mockito.times(1))
                .save(Mockito.eq(ad));
    }

}