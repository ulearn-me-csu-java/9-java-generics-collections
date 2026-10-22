package com.example.task01;

public class Task01Main {
    public static void main(String[] args) {
        Pair<Integer, String> pair = Pair.of(1, "hello");
        System.out.println(pair.getFirst());
        System.out.println(pair.getSecond());

        pair.ifPresent((first, second) -> {
            System.out.println(first + " " + second);
        });

        Pair<Integer, String> pair2 = Pair.of(1, "hello");
        System.out.println(pair.equals(pair2));
        System.out.println(pair.hashCode() == pair2.hashCode());
    }
}
