package com.urise.webapp.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

public class MainConcurrency {
    private static final Object LOCK_A = new Object();
    private static final Object LOCK_B = new Object();

    public static void main(String[] args) {


        System.out.println(minValue(new int[]{1, 2, 3, 3, 2, 3}));
        System.out.println(minValue(new int[]{9, 6}));

        System.out.println(oddOrEven(List.of(1, 2, 3, 3, 2, 3, 1)).toString());
        System.out.println(oddOrEven(List.of(9, 6)).toString());

        StartThread("First Thread", LOCK_A, LOCK_B);
        StartThread("Second Thread", LOCK_B, LOCK_A);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        final Integer[] sum = {integers.stream().mapToInt(Integer::intValue).sum()};

        System.out.println("Сумма = " + sum[0]);

        return integers.stream().mapToInt(Integer::intValue)
                .filter(p -> ((sum[0] % 2 == 0) == (p % 2 != 0))).boxed().collect(Collectors.toList());
    }

    private static int minValue(int[] values) {
        final int[] minValue = {0};
        Arrays.stream(values).sorted().distinct().forEach(p -> minValue[0] = minValue[0] * 10 + p);
        return minValue[0];
    }

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
}
