package com.scaler.LLD.Fundamentals.Class_4.users;

/**
 * LLD 4 — OOP-3: Driver — extends HumanUser, implements Drivable
 *
 * Driver IS A HumanUser (inheritance)  AND  Driver CAN Drive (interface).
 * This combination is the standard Java pattern:
 *   - Inherit shared data/behavior from the abstract class.
 *   - Implement behavior contracts from interfaces.
 */
public class Driver extends HumanUser implements Drivable {

    @Override
    public void acceptRide() {
        System.out.println(name + " (Human Driver) accepted the ride");
    }

    @Override
    public void completeRide() {
        System.out.println(name + " (Human Driver) completed the ride");
    }
}
