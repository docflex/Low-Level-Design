package com.scaler.LLD.Module_1.Part_3_Advanced_Java.Class_2;

import java.util.PriorityQueue;

public class OrderQueue {
    private final PriorityQueue<Order> orders;

    public OrderQueue() {
        this.orders = new PriorityQueue<>(new OrderExpressComparator());
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public Order processOrder() {
        return orders.poll();
    }
}
