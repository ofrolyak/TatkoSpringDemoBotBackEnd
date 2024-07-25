package com.tatko.tatkospringdemobotbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This class is MAIN class for this Spring Boot Application.
 */
@SpringBootApplication
public class TatkoSpringDemoBotBackEndApplication {

    /**
     * MAIN method.
     * @param args
     */
    public static void main(final String[] args) {
        SpringApplication.run(
            TatkoSpringDemoBotBackEndApplication.class, args);
    }

    /**
     * DUMMY for hideutilityclassconstructor by CheckStyle.
     */
    public final void foo() {
        throw new UnsupportedOperationException();
    }
}
