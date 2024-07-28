package com.tatko.tatkospringdemobotbackend;

import lombok.Getter;

/**
 * Parent for all JUnit classes.
 */
@Getter
public class BaseMockTests {

    private final EasyRandomCustom gen = new EasyRandomCustom();

}
