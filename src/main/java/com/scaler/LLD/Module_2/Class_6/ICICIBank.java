package com.scaler.LLD.Module_2.Class_6;

/**
 * Adaptee: stands in for ICICI Bank's third-party SDK -- a different contract from Yes Bank's,
 * including an extra transactionId parameter.
 */
public class ICICIBank {

    public void transferMoney(String accountNumber, double amount, String transactionId) {
        System.out.println("Sending Money Via ICICI Bank: " + amount + " to " + accountNumber + " (txn " + transactionId + ")");
    }

    public double seeCurrentBalance(String accountNumber) {
        System.out.println("Checking Balance Via ICICI Bank");
        return 2500.0;
    }
}
