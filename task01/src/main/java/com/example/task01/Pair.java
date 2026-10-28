package com.example.task01;

import java.util.Objects;
import java.util.function.BiConsumer;

public class Pair<X, Y> {
    X first;
    Y second;

    private Pair(X first, Y second) {
        this.first = first;
        this.second = second;
    }

    public static <X, Y> Pair<X, Y> of(X first, Y second) {
        return new Pair<>(first, second);
    }

    public X getFirst() {
        return first;
    }

    public Y getSecond() {
        return second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair<?, ?> pair = (Pair<?, ?>) o;

        return Objects.equals(first, pair.first) && Objects.equals(second, pair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public String toString() {
        return this.first + " " + this.second;
    }

    public void ifPresent(BiConsumer<? super X, ? super Y> consumer) {
        if ((this.first != null) && (this.second != null)) {
            consumer.accept(this.first, this.second);
        }
    }
}
