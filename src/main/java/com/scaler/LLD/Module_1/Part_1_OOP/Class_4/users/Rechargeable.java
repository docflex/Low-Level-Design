package com.scaler.LLD.Module_1.Part_1_OOP.Class_4.users;

/**
 * LLD 4 — OOP-3: Rechargeable — Interface
 *
 * Applies to RoboDriver but NOT to human Driver.
 * This is exactly why Rechargeable can't be inside Drivable —
 * human drivers aren't rechargeable!
 *
 * A class can implement MULTIPLE interfaces:
 *   RoboDriver implements Drivable, Rechargeable
 */
public interface Rechargeable {

    void recharge();
}
