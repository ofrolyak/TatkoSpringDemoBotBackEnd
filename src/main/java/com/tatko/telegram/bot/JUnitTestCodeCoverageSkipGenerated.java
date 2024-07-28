package com.tatko.telegram.bot;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * This annotation is used to remove method/class from JaCoCo report.
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface JUnitTestCodeCoverageSkipGenerated {
    // https://www.baeldung.com/jacoco-report-exclude
}
