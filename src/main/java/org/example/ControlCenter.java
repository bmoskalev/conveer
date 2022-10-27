package org.example;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ControlCenter {
    private final int ispMechCount;
    private final StatusStorage statusStorage;

    public ControlCenter(int ispMechCount,StatusStorage statusStorage) {
        this.ispMechCount = ispMechCount;
        this.statusStorage = statusStorage;
    }

    public void start() {
        long step = 0;
        while (true) {
            List<Thread> threadList = new ArrayList<>();
            statusStorage.cleanStorage();
            for (int i = 0; i < ispMechCount; i++) {
                Thread thread = new Thread(new IspMech(i, statusStorage));
                threadList.add(thread);
                thread.start();
            }
            for (int i = 0; i < ispMechCount; i++) {
                Thread thread = threadList.get(i);
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            boolean failed = false;
            Date date = new Date();
            for (int i = 0; i < ispMechCount; i++) {
                switch (statusStorage.getStatus(i)) {
                    case SUCCESS -> {
                    }
                    case FAIL -> {
                        System.out.println(date + " Process on " + (i + 1) + " IM is failed.");
                        failed = true;
                    }
                    case NOT_ANSWERED -> {
                        System.out.println(date + " IM " + (i + 1) + " isn't answered.");
                        failed = true;
                    }
                }
            }
            step++;
            System.out.println(date + " Step " + step + " is " + (failed ? "failed" : "succeed"));
            if (failed) {
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
