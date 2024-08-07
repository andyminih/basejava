package com.urise.webapp;

import java.util.Arrays;
import java.util.List;

public class MainIntStream {

    public static void main(String[] args) {

        System.out.println(minValue(new int[]{1, 2, 3, 3, 2, 3}));
        System.out.println(minValue(new int[]{9, 6}));

        System.out.println(oddOrEven(List.of(1, 2, 3, 3, 2, 3, 1)).toString());
        System.out.println(oddOrEven(List.of(9, 6)).toString());

    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        final int sum = integers.stream().mapToInt(Integer::intValue).sum();

        System.out.println("����� = " + sum);

        return integers.stream().filter(p -> ((sum % 2 == 0) == (p % 2 != 0))).toList();
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values).sorted().distinct().reduce(0, (res, p) -> res * 10 + p);
    }

}
