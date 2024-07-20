package com.tatko.tatkospringdemobotbackend;

import org.jeasy.random.EasyRandom;

public class EasyRandomCustom extends EasyRandom {

    public String nextString() {
        return this.nextObject(String.class);
    }
}
