package com.tatko.tatkospringdemobotbackend;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
//@ExtendWith(MockitoExtension.class)
//@EnableAutoConfiguration(exclude = {
////        DataSourceAutoConfiguration.class,
////        DataSourceTransactionManagerAutoConfiguration.class,
////        HibernateJpaAutoConfiguration.class,
////        JpaRepositoriesAutoConfiguration.class,
//////
//        DataSourceAutoConfiguration.class
////        DataSourceTransactionManagerAutoConfiguration.class,
////        HibernateJpaAutoConfiguration.class
//})
public class BaseMockTests {

    public EasyRandomCustom gen = new EasyRandomCustom();



}
