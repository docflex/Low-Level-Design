package com.scaler.LLD.Fundamentals.Class_5;

public class DummyClass implements Lendable {
    @Override
    public boolean lend(User user) {
        return false;
    }

    @Override
    public void returnItem(User user) {

    }

    @Override
    public boolean isAvailable() {
        return false;
    }
}
