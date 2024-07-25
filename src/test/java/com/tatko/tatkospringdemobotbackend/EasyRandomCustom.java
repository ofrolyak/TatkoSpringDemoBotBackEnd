package com.tatko.tatkospringdemobotbackend;

import org.jeasy.random.EasyRandom;

/**
 * Expansion functionality for EasyRandom functionality.
 */
public class EasyRandomCustom extends EasyRandom {

    /**
     * Generate String object.
     *
     * @return Get generated random String object.
     */
    public String nextString() {
        return this.nextObject(String.class);
    }
}
