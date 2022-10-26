package org.example;


public class ControlCenter implements Runnable{
    private final int ispMechCount;
    private final StatusStorage statusStorage;

    public ControlCenter(int ispMechCount, StatusStorage statusStorage) {
        this.ispMechCount = ispMechCount;
        this.statusStorage = statusStorage;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(500);
            boolean failed = false;
            for (int i = 0; i < ispMechCount; i++) {
                switch (statusStorage.getStatus(i)) {
                    case SUCCESS -> {
                    }
                    case FAIL -> {
                        System.out.println("Process on " + (i + 1) + " IM is failed.");
                        failed = true;
                    }
                    case NOT_ANSWERED -> {
                        System.out.println("IM " + (i + 1) + " isn't answered.");
                        failed = true;
                    }
                }
            }
        } catch (InterruptedException e) {}

    }
}
