package ru.basejava.iliketobreathe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainStreams {
    public static void main(String[] args) {
        int[] array = {1, 4, 3, 1, 5, 4, 1, 3};
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 4, 3, 1, 5, 4, 1, 3));

        System.out.println(minValue(array));
        System.out.println(oddOrEven(list));
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values).sorted().distinct().reduce((x, y) -> x * 10 + y).orElse(0);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream().mapToInt(Integer::intValue).sum();

        return integers.stream().filter(x -> {
            boolean a = false;
            if (sum % 2 == 0) {
                if (x % 2 != 0) {
                    return a = true;
                }
            } else {
                if (x % 2 == 0) {
                    return a = true;
                }
            }
            return a;
        }).collect(Collectors.toList());
    }
}
