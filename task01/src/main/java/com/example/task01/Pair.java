package com.example.task01;

import java.util.Objects;
import java.util.function.BiConsumer;

public class Pair<A, B> {
    private final A first;
    private final B second;

    private Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public static <A, B> Pair<A, B> of(A first, B second) {
        return new Pair<>(
                Objects.requireNonNull(first),
                Objects.requireNonNull(second));
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }

    public void ifPresent(BiConsumer<? super A, ? super B> consumer) {
        consumer.accept(first, second);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return first.equals(pair.first) && second.equals(pair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}