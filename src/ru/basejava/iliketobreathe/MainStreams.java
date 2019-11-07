package ru.basejava.iliketobreathe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MainStreams {
    public static void main(String[] args) {
        int[] array = {1, 4, 3, 1, 5, 4, 1, 3};
        List<Integer> list = new ArrayList<>();
        list = Arrays.asList(1, 4, 3, 1, 5, 4, 1, 3);

        System.out.println(minValue(array));

        System.out.println(oddOrEven(list));
    }

    private static int minValue(int[] values) {
        var ref = new Object() {
            int i = 0;
        };

        AtomicInteger result = new AtomicInteger();

        Arrays.stream(values).boxed().sorted(Collections.reverseOrder()).distinct().forEach(x -> {
            int pos = (int)Math.pow(10, ref.i++);
            result.addAndGet(x * pos);
        });

        return result.get();
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {

        List<Integer> newIntegers = new ArrayList<>();

        int sum = integers.stream().mapToInt(Integer::intValue).sum();

        integers.stream().forEach(x -> {
            if (sum % 2 == 0) {
                if (x % 2 == 0) {
                    newIntegers.add(x);
                }
            } else {
                if (x % 2 != 0) {
                    newIntegers.add(x);
                }
            }

        });

        return newIntegers;
    }
}
