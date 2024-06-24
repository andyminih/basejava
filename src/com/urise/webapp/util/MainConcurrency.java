package com.urise.webapp.util;

import static java.lang.Thread.sleep;

public class MainConcurrency {
    private static final Object LOCK_A = new Object();
    private static final Object LOCK_B = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println("First thread started");
            System.out.println("First thread - about to lock LOCK_A");
            synchronized (LOCK_A) {
                System.out.println("First thread - LOCK_A locked");
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("First thread - about to lock LOCK_B");
                synchronized (LOCK_B) {
                    System.out.println("First thread - LOCK_B locked");
                }
                ;
                System.out.println("First thread - LOCK_B unlocked");
            }
            ;
            System.out.println("First thread - LOCK_A unlocked");
            System.out.println("First thread done");
        }).start();

        new Thread(() -> {
            System.out.println("Second thread started");
            System.out.println("Second thread - about to lock LOCK_B");
            synchronized (LOCK_B) {
                System.out.println("Second thread - LOCK_B locked");
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Second thread - about to lock LOCK_A");
                synchronized (LOCK_A) {
                    System.out.println("Second thread - LOCK_A locked");
                }
                ;
                System.out.println("Second thread - LOCK_A unlocked");
            }
            ;
            System.out.println("Second thread - LOCK_B unlocked");
            System.out.println("Second thread done");
        }).start();
    }
}
