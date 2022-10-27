package org.example;

public class IspMech implements Runnable {
    private final int id;
    private final StatusStorage statusStorage;
    public IspMech(int id, StatusStorage statusStorage) {
        this.id = id;
        this.statusStorage = statusStorage;
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
