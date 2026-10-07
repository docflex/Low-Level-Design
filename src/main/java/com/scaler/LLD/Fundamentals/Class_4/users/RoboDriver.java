package com.scaler.LLD.Fundamentals.Class_4.users;

/**
 * LLD 4 — OOP-3: RoboDriver — implements Drivable AND Rechargeable
 *
 * RoboDriver is NOT a HumanUser (no name, no login).
 * But it CAN Drive (Drivable) and CAN be recharged (Rechargeable).
 *
 * WITHOUT interfaces, we'd need multiple inheritance:
 *   RoboDriver extends HumanUser, DrivingUser  ← NOT ALLOWED IN JAVA!
 *
 * Interfaces solve this:
 *   - RoboDriver implements Drivable, Rechargeable
 *   - No diamond problem, no forced hierarchy.
 *   - A class can implement as many interfaces as it needs.
 */
public class RoboDriver implements Drivable, Rechargeable {

    private final String modelId;

    public RoboDriver(String modelId) {
        this.modelId = modelId;
    }

    @Override
    public void acceptRide() {
        System.out.println("RoboDriver [" + modelId + "] accepted the ride");
    }

    @Override
    public void completeRide() {
        System.out.println("RoboDriver [" + modelId + "] completed the ride");
    }

    @Override
    public void recharge() {
        System.out.println("RoboDriver [" + modelId + "] is recharging...");
    }

    @Override
    public String toString() {
        return "RoboDriver{modelId='" + modelId + "'}";
    }
}
