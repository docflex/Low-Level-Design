package com.scaler.LLD.Module_1.Part_3_Advanced_Java.Class_2;

import java.util.Comparator;

public class OrderExpressComparator implements Comparator<Order> {
    @Override
    public int compare(Order o1, Order o2) {
        if (o1.isExpress() && !o2.isExpress()) return -1;
        if (!o1.isExpress() && o2.isExpress()) return 1;
        return 0;
    }
}
