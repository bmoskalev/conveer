package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

public class Main {
    public static void main(String[] args) {
        final int NUMBER_ISP_MECH = 10;
        StatusStorage statusStorage = new HashMapStatusStorage(new ConcurrentHashMap<>());
        CyclicBarrier cyclicBarrier = new CyclicBarrier(NUMBER_ISP_MECH,new ControlCenter(NUMBER_ISP_MECH,statusStorage));
        long step = 1;
        while (true) {
            List<Thread> threadList = new ArrayList<>();
            statusStorage.cleanStorage();
            for (int i = 0; i < NUMBER_ISP_MECH; i++) {
                Thread thread = new Thread(new IspMech(i, statusStorage,cyclicBarrier));
                threadList.add(thread);
                thread.start();
            }
            for (int i = 0; i < NUMBER_ISP_MECH; i++) {
                Thread thread = threadList.get(i);
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            boolean failed = false;
            for (int i = 0; i < NUMBER_ISP_MECH; i++) {
                switch (statusStorage.getStatus(i)) {
                    case SUCCESS -> {
                    }
                    case FAIL -> {
                        System.out.println("Process on " + (i+1) + " IM is failed.");
                        failed = true;
                    }
                    case NOT_ANSWERED -> {
                        System.out.println("IM " + (i+1) + " isn't answered.");
                        failed = true;
                    }
                }
            }
            for (int i = 0; i < NUMBER_ISP_MECH; i++) {
                Thread thread = threadList.get(i);
                if (thread.isAlive()) {
                    thread.interrupt();
                }
            }
            if (!failed) {
                System.out.println("Step " + step + " is succeed");
                step++;
            } else {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                while (true) {
                    System.out.print("Continue? (Y/N):");
                    try {
                        String answer = bufferedReader.readLine();
                        if ("Y".equalsIgnoreCase(answer)) {
                            break;
                        } else if ("N".equalsIgnoreCase(answer)) {
                            return;
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}