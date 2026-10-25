package com.scaler.LLD.Module_1.Part_3_Advanced_Java.Class_1;

/**
 * Java Advanced Concepts - 1 [Generics]
 *
 * Bounded type parameter example: T and V must extend Number.
 * This enables arithmetic operations like sum() that wouldn't
 * be possible on an unbounded generic (where T could be String).
 */
public class NumberPair<T extends Number, V extends Number> {
    private final T x;
    private final V y;

    public NumberPair(T x, V y) {
        this.x = x;
        this.y = y;
    }

    public T getX() {
        return x;
    }

    public V getY() {
        return y;
    }

    /**
     * This method is only possible because T and V are bounded to Number.
     * Number provides doubleValue(), intValue(), etc.
     */
    public double sum() {
        return x.doubleValue() + y.doubleValue();
    }

    @Override
    public String toString() {
        return "NumberPair{x=" + x + ", y=" + y + ", sum=" + sum() + "}";
    }
}
