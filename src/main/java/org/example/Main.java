package org.example;

public class Main {

    private static final Object MONITOR = new Object();
    private static final String PING = "Ping";
    private static final String PONG = "Pong";
    private static final Integer SHOTS_COUNT = 5;
    private static String nextStep = PING;

    public static void main(String[] args) {
        printShot(PING, PONG);
        printShot(PONG, PING);
    }

    private static void printShot(String firstShot, String secondShot) {
        new Thread(() -> {
            synchronized (MONITOR) {
                for (int i = 0; i < Main.SHOTS_COUNT; i++) {
                    try {
                        while (!nextStep.equals(firstShot)) {
                            MONITOR.wait();
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.print(firstShot + "..");
                    nextStep = secondShot;
                    MONITOR.notifyAll();
                }
            }
        }).start();
    }
}
