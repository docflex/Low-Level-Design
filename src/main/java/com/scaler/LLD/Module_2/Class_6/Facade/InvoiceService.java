package com.scaler.LLD.Module_2.Class_6.Facade;

/** Subsystem: billing. */
public class InvoiceService {
    public void generateInvoice(String customerName, String orderDetails) {
        System.out.println("  [Invoice]   Generating invoice for " + customerName + ": " + orderDetails);
    }
}
