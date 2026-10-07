package com.scaler.LLD.Fundamentals.Class_4.payments;

/**
 * LLD 4 — OOP-3: Composition vs Inheritance Demo
 *
 * TEACHING FLOW:
 *   1. Show TripService using RazorPayGateway.
 *   2. Switch to PaytmGateway — only the constructor argument changes.
 *   3. TripService code is untouched. That's the power of composition + interfaces.
 *
 * Run: Right-click → Run 'CompositionDemo.main()'
 */
public class CompositionDemo {

    public static void main(String[] args) {

        // ══════════════════════════════════════════════════════════════
        //  DEMO 1: Composition — HAS-A Relationship
        // ══════════════════════════════════════════════════════════════

        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║  DEMO 1: Composition — TripService HAS-A PG   ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        System.out.println("Inheritance -> IS-A:    Car IS A Vehicle");
        System.out.println("Composition -> HAS-A:   TripService HAS A PaymentGateway");
        System.out.println();

        // Using RazorPay
        TripService trip1 = new TripService(new RazorPayGateway());
        trip1.completeTrip(250.0);


        // ══════════════════════════════════════════════════════════════
        //  DEMO 2: Swapping Implementations — Zero Code Change
        // ══════════════════════════════════════════════════════════════

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  DEMO 2: Swap RazorPay -> Paytm (one-line change)║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        // Switching to Paytm — only this line changes:
        TripService trip2 = new TripService(new PaytmGateway());
        trip2.completeTrip(250.0);

        System.out.println();
        System.out.println("TripService.completeTrip() is IDENTICAL in both cases.");
        System.out.println("Only the constructor argument changed.");
        System.out.println("This is the power of programming to an interface.");


        // ══════════════════════════════════════════════════════════════
        //  WHEN TO USE WHICH
        // ══════════════════════════════════════════════════════════════

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  Inheritance vs Composition — When to Use What ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        System.out.println("  Inheritance (IS-A):");
        System.out.println("    Car IS A Vehicle         -> Car extends Vehicle");
        System.out.println("    Dog IS AN Animal         -> Dog extends Animal");
        System.out.println();
        System.out.println("  Composition (HAS-A):");
        System.out.println("    TripService HAS A PG     -> TripService contains PaymentGateway");
        System.out.println("    Car HAS AN Engine        -> Car contains an Engine object");
        System.out.println("    Elephant HAS A Cage      -> Elephant contains a Cage reference");
        System.out.println();
        System.out.println("  Quick test: Does 'X IS A Y' make grammatical sense?");
        System.out.println("    Yes -> consider inheritance.");
        System.out.println("    No  -> use composition.");
        System.out.println("    'Elephant IS A Cage' -> nonsense. Use HAS-A.");
    }
}
