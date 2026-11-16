package com.scaler.LLD.Module_2.Class_6;

/**
 * Adaptee: stands in for Yes Bank's third-party SDK. We don't own it and can't change its
 * method names, so it does NOT implement our BankAPI.
 */
public class YesBank {
    public void makeTransaction(String accountNumber, double amount) {
        System.out.println("Sending Money Via Yes Bank: " + amount + " to " + accountNumber);
    }

    public double getBalance(String accountNumber) {
        System.out.println("Checking Balance Via Yes Bank");
        return 1200.0;
    }
}
