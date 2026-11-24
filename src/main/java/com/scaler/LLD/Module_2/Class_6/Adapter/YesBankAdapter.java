package com.scaler.LLD.Module_2.Class_6.Adapter;

import com.scaler.LLD.Module_2.Class_6.BankAPI;
import com.scaler.LLD.Module_2.Class_6.YesBank;

/** Adapter: translates our BankAPI calls into Yes Bank's method names. */
public class YesBankAdapter implements BankAPI {

    private final YesBank yesBank;

    public YesBankAdapter() {
        this(new YesBank());
    }

    /** Lets a test pass in a fake YesBank. */
    public YesBankAdapter(YesBank yesBank) {
        this.yesBank = yesBank;
    }

    @Override
    public void send(String accountNumber, double amount) {
        yesBank.makeTransaction(accountNumber, amount);
    }

    @Override
    public double checkBalance(String accountNumber) {
        return yesBank.getBalance(accountNumber);
    }
}
