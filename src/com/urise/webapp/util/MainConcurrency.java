package com.urise.webapp.util;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

public class MainConcurrency {
    private static final Object LOCK_A = new Object();
    private static final Object LOCK_B = new Object();

    private static void StartThread(String name, Object lockObject1, Object lockObject2) {
        new Thread(() -> {
            System.out.println(currentThread().getName() + " started");
            System.out.println(currentThread().getName() + " is about to lock " + lockObject1);
            synchronized (lockObject1) {
                System.out.println(currentThread().getName() + " has locked " + lockObject1);
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(currentThread().getName() + " is about to lock " + lockObject2);
                synchronized (lockObject2) {
                    System.out.println(currentThread().getName() + " has locked " + lockObject2);
                }
                System.out.println(currentThread().getName() + " has unlocked " + lockObject2);
            }
            System.out.println(currentThread().getName() + " has unlocked " + lockObject1);
            System.out.println(currentThread().getName() + " is done");
        }, name).start();
    }

    public static void main(String[] args) {
        StartThread("First Thread", LOCK_A, LOCK_B);
        StartThread("Second Thread", LOCK_B, LOCK_A);
    }
}
