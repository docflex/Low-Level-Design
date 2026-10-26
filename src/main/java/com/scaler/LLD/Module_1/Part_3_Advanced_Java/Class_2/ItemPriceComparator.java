package com.scaler.LLD.Module_1.Part_3_Advanced_Java.Class_2;

import java.util.Comparator;

public class ItemPriceComparator implements Comparator<Item> {
    @Override
    public int compare(Item o1, Item o2) {
        return Double.compare(o1.getPrice(), o2.getPrice()); // Sorts on the Basis of Price ASC
    }
}
