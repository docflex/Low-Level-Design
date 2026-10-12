package com.scaler.LLD.Module_1.Part_1_OOP.Class_4.payments;

public class RazorPayGateway implements PaymentGateway {

    @Override
    public void pay(double amount) {
        System.out.println("RazorPay: paid Rs " + amount);
    }

    @Override
    public String getGatewayName() {
        return "RazorPay";
    }
}
