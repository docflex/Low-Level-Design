package com.scaler.LLD.Fundamentals.Class_4.payments;

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
