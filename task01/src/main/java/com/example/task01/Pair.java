package com.example.task01;

import java.util.Objects;
import java.util.function.BiConsumer;

public final class Pair<T1, T2> {
    private final T1 first;
    private final T2 second;

    private Pair(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }

    public static <T1, T2> Pair<T1, T2> of(T1 first, T2 second) {
        return new Pair<>(first, second);
    }

    public T1 getFirst() { return first; }

    public T2 getSecond() { return second; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair<?, ?>)) return false;
        Pair<?, ?> other = (Pair<?, ?>) o;
        return Objects.equals(first, other.first) && Objects.equals(second, other.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    public boolean ifPresent() {
        return first != null && second != null;
    }

    public void ifPresent(BiConsumer<? super T1, ? super T2> action) {
        if (ifPresent())
           action.accept(getFirst(), getSecond());
    }
}
