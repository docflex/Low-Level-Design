package com.scaler.LLD.Module_2.Class_6;

/**
 * Target interface: the contract PhonePe wants to work with.
 * Every bank's own API is adapted to this through an Adapter.
 */
public interface BankAPI {
    void send(String accountNumber, double amount);
    double checkBalance(String accountNumber);
}
