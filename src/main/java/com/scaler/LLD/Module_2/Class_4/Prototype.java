package com.scaler.LLD.Module_2.Class_4;

/**
 * Anything that can be copied implements this. The client calls clone() without knowing
 * the concrete class -- each class copies its own fields.
 */
public interface Prototype<T> {
    T clone();
}
