package org.example;

import java.util.concurrent.ConcurrentHashMap;

public class Main {
    public static void main(String[] args) {
        final int NUMBER_ISP_MECH = 10;
        StatusStorage statusStorage = new HashMapStatusStorage(new ConcurrentHashMap<>());
        ControlCenter controlCenter = new ControlCenter(NUMBER_ISP_MECH,statusStorage);
        controlCenter.start();
    }
}