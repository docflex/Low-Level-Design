package com.scaler.LLD.Module_2.Class_6;

import java.util.Objects;

/**
 * Client of the Target interface. PhonePe only knows BankAPI, so switching banks
 * (e.g. Yes Bank -> ICICI Bank) needs no change here.
 */
public class PhonePe {
    private final BankAPI bankAPI;

    public PhonePe(BankAPI bankAPI) {
        this.bankAPI = Objects.requireNonNull(bankAPI, "bankAPI");
    }

    void sendMoney(String accountNumber, double amount) {
        bankAPI.send(accountNumber, amount);
    }

    double checkBalance(String accountNumber) {
        return bankAPI.checkBalance(accountNumber);
    }
}
