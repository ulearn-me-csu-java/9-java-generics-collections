package com.example.task01;

import java.util.Objects;
import java.util.function.BiConsumer;

public class Pair<T, P> {
    private final T firstValue;
    private final P secondValue;

    private Pair(T firstValue, P secondValue){
        this.firstValue = firstValue;
        this.secondValue = secondValue;
    }

    public T getFirst(){ return firstValue;}
    public P getSecond(){ return secondValue;}

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Pair<?, ?> other = (Pair<?, ?>) obj;
        return Objects.equals(this.firstValue, other.firstValue) &&
                Objects.equals(this.secondValue, other.secondValue);
    }

    @Override
    public int hashCode(){
        return Objects.hash(firstValue, secondValue);
    }

    public static <T, P> Pair<T, P> of(T first, P second){
            return new Pair<>(first,second);
    }

    public void ifPresent(BiConsumer<? super T, ? super P> action){
        if (firstValue != null && secondValue != null){
            action.accept(firstValue,secondValue);
        }
    }
}
