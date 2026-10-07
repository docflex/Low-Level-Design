package com.scaler.LLD.Fundamentals.Class_4.users;

/**
 * LLD 4 — OOP-3: Customer — extends HumanUser
 *
 * Customer IS A HumanUser but does NOT implement Drivable.
 * Customers don't accept or complete rides — only Drivers do.
 */
public class Customer extends HumanUser {

    public void bookRide() {
        System.out.println(name + " booked a ride");
    }
}
