package com.scaler.LLD.Module_1.Part_3_Advanced_Java.Class_1;

public class Pair<T, V> {
    private final T x;
    private final V y;

    public Pair(T x, V y) {
        this.x = x;
        this.y = y;
    }

    public T getX() {
        return x;
    }

    public V getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    /**
     * Static methods cannot use the class-level type parameter T
     * because T is only defined at object creation time.
     * Instead, we declare a method-level generic &lt;S&gt;.
     */
    public static <S> void doSomething(S x) {
        System.out.println(x);
    }
}
