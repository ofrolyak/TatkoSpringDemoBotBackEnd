package com.tatko.telegram.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * This class is MAIN class for this Spring Boot Application.
 */

@SpringBootApplication
@Slf4j
public class TatkoSpringDemoBotBackEndApplication {

    /**
     * MAIN method.
     * @param args Input arguments.
     */
    public static void main(final String[] args) {
        log.info("TatkoSpringDemoBotBackEndApplication started");
        SpringApplication.run(
            TatkoSpringDemoBotBackEndApplication.class, args);
        log.info("TatkoSpringDemoBotBackEndApplication finished");
    }

    /**
     * DUMMY for HideUtilityClassConstructor by CheckStyle.
     */
    @SuppressWarnings("unused")
    public final void foo() {
        throw new UnsupportedOperationException();
    }
}
