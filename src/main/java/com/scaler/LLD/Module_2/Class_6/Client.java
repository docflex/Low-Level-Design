package com.scaler.LLD.Module_2.Class_6;

import com.scaler.LLD.Module_2.Class_6.Factory.BankAPIFactory;

/** Adapter + Factory at runtime: PhonePe works with any bank through the same BankAPI. */
public class Client {
    public static void main(String[] args) {
        System.out.println("=== Registered banks: " + BankAPIFactory.providers() + " ===");

        for (String bank : new String[]{"Yes Bank", "ICICI Bank"}) {
            System.out.println("\n=== PhonePe on " + bank + " ===");
            BankAPI bankAPI = BankAPIFactory.getBankAPI(bank);
            PhonePe phonePe = new PhonePe(bankAPI);
            phonePe.sendMoney("123", 3.2);
            System.out.println("  balance = " + phonePe.checkBalance("123"));
        }

        System.out.println("\n=== Lookup is case-insensitive ===");
        System.out.println("  \"icici bank\" -> " + BankAPIFactory.getBankAPI("icici bank").getClass().getSimpleName());

        System.out.println("\n=== Unknown bank fails immediately ===");
        try {
            BankAPIFactory.getBankAPI("HDFC Bank");
        } catch (IllegalArgumentException e) {
            System.out.println("  " + e.getMessage());
        }
    }
}
