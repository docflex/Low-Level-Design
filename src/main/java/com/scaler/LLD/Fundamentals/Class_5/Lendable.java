package com.scaler.LLD.Fundamentals.Class_5;

public interface Lendable {
    boolean lend(User user);
    void returnItem(User user);
    boolean isAvailable();
}
