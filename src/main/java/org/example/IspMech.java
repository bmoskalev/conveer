package org.example;

import java.util.concurrent.CyclicBarrier;

public class IspMech implements Runnable {
    private final int id;
    private final StatusStorage statusStorage;
    CyclicBarrier cyclicBarrier;

    public IspMech(int id, StatusStorage statusStorage, CyclicBarrier cyclicBarrier) {
        this.id = id;
        this.statusStorage = statusStorage;
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
//         while (!Thread.currentThread().isInterrupted()) {
        try {
            Thread.sleep(500);
            int randomInt = (int) (Math.random() * 1001);
            if (randomInt == 1000) {
                statusStorage.saveStatus(id, Status.NOT_ANSWERED);
            } else if (randomInt == 999) {
                statusStorage.saveStatus(id, Status.FAIL);
            } else {
                statusStorage.saveStatus(id, Status.SUCCESS);
            }
//            cyclicBarrier.await();
//        }
        } catch (Exception e) {
        }
    }
}
