package com.scaler.LLD.Module_1.Part_1_OOP.Class_5;

public interface Lendable {
    boolean lend(User user);
    void returnItem(User user);
    boolean isAvailable();
}
