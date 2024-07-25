package com.tatko.tatkospringdemobotbackend;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Parent class for JUnit classes that is build by Mockito.
 */
@ExtendWith(MockitoExtension.class)
public class MockitoExtensionBaseMockTests extends BaseMockTests {

    /**
     * Generator for random entities.
     */
    public EasyRandomCustom gen = new EasyRandomCustom();



}
