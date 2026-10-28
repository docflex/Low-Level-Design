package com.scaler.LLD.Module_1.Part_3_Advanced_Java.Class_3;

import java.util.Comparator;

public class SquaresComparator implements Comparator<Integer> {
    @Override
    public int compare(Integer o1, Integer o2) {
        return Integer.compare(o1 * o1, o2 * o2);
    }
}
