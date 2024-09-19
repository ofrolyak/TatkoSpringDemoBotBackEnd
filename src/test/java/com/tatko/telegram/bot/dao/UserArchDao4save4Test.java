package com.tatko.telegram.bot.dao;

import com.tatko.telegram.bot.MockitoExtensionBaseMockTests;
import com.tatko.telegram.bot.entity.UserArch;
import com.tatko.telegram.bot.repository.UserArchRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

class UserArchDao4save4Test extends MockitoExtensionBaseMockTests {

    @Mock
    UserArchRepository userArchRepository;
    @InjectMocks
    UserArchDao userArchDao;

    @Test
    void save4success4Test() {

        // Before
        UserArch userArch = getGen().nextUserArch();

        // When
        Mockito.when(userArchRepository.save(Mockito.eq(userArch)))
                .thenReturn(userArch);

        // Action
        UserArch userArchSaved = userArchDao.save(userArch);

        // Then
        assertThat(userArchSaved)
                .isEqualTo(userArch);
        Mockito.verify(userArchRepository, Mockito.times(1))
                .save(Mockito.eq(userArch));
    }

}