package com.scaler.LLD.Module_2.Class_6.Adapter;

import com.scaler.LLD.Module_2.Class_6.BankAPI;
import com.scaler.LLD.Module_2.Class_6.ICICIBank;

import java.util.UUID;

/**
 * Adapter: translates our BankAPI calls into ICICI's contract, including the extra
 * transactionId parameter ICICI needs and our interface doesn't have.
 */
public class ICICIBankAdapter implements BankAPI {
    private final ICICIBank iciciBank;

    public ICICIBankAdapter() {
        this(new ICICIBank());
    }

    /** Lets a test pass in a fake ICICIBank. */
    public ICICIBankAdapter(ICICIBank iciciBank) {
        this.iciciBank = iciciBank;
    }

    @Override
    public void send(String accountNumber, double amount) {
        // The Adapter will hold the Logic for the Extra API Attributes.
        // Note: a new ID per call means a retried request becomes a NEW transfer. In production the
        // caller would pass an idempotency key so the bank can recognise a retry.
        String transactionID = UUID.randomUUID().toString();
        iciciBank.transferMoney(accountNumber, amount, transactionID);
    }

    @Override
    public double checkBalance(String accountNumber) {
        return iciciBank.seeCurrentBalance(accountNumber);
    }
}
