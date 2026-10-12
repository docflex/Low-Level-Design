package com.scaler.LLD.Module_1.Part_1_OOP.Class_4.payments;

public class PaytmGateway implements PaymentGateway {

    @Override
    public void pay(double amount) {
        System.out.println("Paytm: paid Rs " + amount);
    }

    @Override
    public String getGatewayName() {
        return "Paytm";
    }
}
