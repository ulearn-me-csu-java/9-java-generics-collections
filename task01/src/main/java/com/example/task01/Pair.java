package com.example.task01;
import java.util.Objects;
import java.util.function.BiConsumer;

public class Pair<T, U> {
    private final T first;
    private final U second;

    private Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    public static <T, U> Pair<T, U> of(T first, U second) {
        return new Pair<>(first, second);
    }

    // Получение первого элемента
    public T getFirst() {
        return first;
    }

    // Получение второго элемента
    public U getSecond() {
        return second;
    }

    // Сравнение объектов
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Pair<?, ?> pair)) {
            return false;
        }

        return Objects.equals(first, pair.first)
                && Objects.equals(second, pair.second);
    }

    // Вычисление хеш-кода
    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    // Выполнение действия, если оба элемента не null
    public void ifPresent(BiConsumer<? super T, ? super U> consumer) {
        if (first != null && second != null) {
            consumer.accept(first, second);
        }
    }


}
