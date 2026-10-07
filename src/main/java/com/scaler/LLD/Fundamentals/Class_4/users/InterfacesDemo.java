package com.scaler.LLD.Fundamentals.Class_4.users;

import java.util.List;

/**
 * LLD 4 — OOP-3: Interfaces Demo (Uber Domain)
 *
 * TEACHING FLOW:
 *   1. HumanUser / Driver / Customer — abstract class for IS-A.
 *   2. Drivable interface — Driver and RoboDriver both CAN drive.
 *   3. Rechargeable interface — only RoboDriver is rechargeable.
 *   4. Polymorphism through interfaces (List<Drivable>).
 *
 * See also: birds/ClassExplosionDemo for the Bird / Class-Explosion example.
 *
 * Run: Right-click → Run 'InterfacesDemo.main()'
 */
public class InterfacesDemo {

    public static void main(String[] args) {

        // ══════════════════════════════════════════════════════════════
        //  DEMO 1: Abstract Class + Interface Together
        // ══════════════════════════════════════════════════════════════

        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║  DEMO 1: HumanUser + Drivable Interface        ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        Driver driver = new Driver();
        driver.name = "Rehber";
        driver.age = 28;
        driver.login();          // inherited from HumanUser
        driver.acceptRide();     // from Drivable interface

        Customer customer = new Customer();
        customer.name = "Alice";
        customer.age = 25;
        customer.login();        // inherited from HumanUser
        customer.bookRide();     // Customer-specific

        // customer.acceptRide();  // COMPILE ERROR — Customer doesn't implement Drivable


        // ══════════════════════════════════════════════════════════════
        //  DEMO 2: RoboDriver — No HumanUser, but CAN Drive
        // ══════════════════════════════════════════════════════════════

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  DEMO 2: RoboDriver — Drivable + Rechargeable  ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        RoboDriver robo = new RoboDriver("TESLA-X100");
        robo.acceptRide();       // from Drivable
        robo.completeRide();     // from Drivable
        robo.recharge();         // from Rechargeable

        // robo.login();  // COMPILE ERROR — RoboDriver is NOT a HumanUser


        // ══════════════════════════════════════════════════════════════
        //  DEMO 3: Polymorphism via Interface
        // ══════════════════════════════════════════════════════════════

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  DEMO 3: Interface Polymorphism                ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        // Both Driver and RoboDriver implement Drivable.
        // We can treat them uniformly through the interface reference.
        List<Drivable> allDrivers = List.of(driver, robo);

        System.out.println("Dispatching rides to all Drivable entities:");
        for (Drivable d : allDrivers) {
            d.acceptRide();      // runtime polymorphism via interface
        }


        // ══════════════════════════════════════════════════════════════
        //  SUMMARY: IS-A vs CAN-DO
        // ══════════════════════════════════════════════════════════════

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  When to use WHAT?                             ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        System.out.println("  Abstract Class -> IS-A relationship (nouns)");
        System.out.println("    Driver IS A HumanUser");
        System.out.println();
        System.out.println("  Interface -> CAN-DO behavior (verbs)");
        System.out.println("    Driver CAN Drive (Drivable)");
        System.out.println("    RoboDriver CAN be Recharged (Rechargeable)");
        System.out.println();
        System.out.println("  See also: birds.ClassExplosionDemo for how interfaces");
        System.out.println("  prevent 2^N class explosion.");
    }
}
