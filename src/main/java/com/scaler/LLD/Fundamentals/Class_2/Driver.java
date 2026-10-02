package com.scaler.LLD.Fundamentals.Class_2;

/**
 * LLD 2 — OOP-1: The Driver Class (Uber Domain)
 *
 * This is the BLUEPRINT for a Driver entity. Key concepts:
 *
 *   CLASS = Blueprint          → defines what a Driver IS (fields + methods)
 *   OBJECT = Instance          → an actual Driver occupying memory on the heap
 *   STATE = Field values       → the values held at any given point in time
 *   MEMBER VARIABLE = field    → driverId, name, rating, isOnline
 *   MEMBER FUNCTION = method   → acceptRide(), changeOnlineStatus()
 *
 * TOPICS DEMONSTRATED:
 *   - Fields with Java default values (int→0, double→0.0, boolean→false, String→null)
 *   - Constructor overloading (no-arg, single-param, fully parameterized)
 *   - The `this` keyword (disambiguating field vs parameter names)
 *   - Static variable (totalDriverCount — shared across all objects)
 *   - Static block (runs once at class loading, before any constructor)
 *   - Static method (belongs to the class, no `this` context)
 *   - toString() override
 */
public class Driver {

    // ══════════════════════════════════════════════════════════════════
    //  MEMBER VARIABLES (Instance Fields)
    //  Each object gets its OWN copy of these.
    // ══════════════════════════════════════════════════════════════════

    int driverId;
    String name;
    double rating;
    boolean isOnline;

    // ══════════════════════════════════════════════════════════════════
    //  STATIC VARIABLE
    //  ONE copy shared by ALL objects. Belongs to the CLASS.
    //  Stored on the Java Heap (as part of Class<Driver>, Java 8+).
    //
    //  Access via class name:  Driver.totalDriverCount   (correct)
    //  Not via object:         d1.totalDriverCount       (bad practice — IDE warning)
    // ══════════════════════════════════════════════════════════════════

    static int totalDriverCount;

    // ══════════════════════════════════════════════════════════════════
    //  STATIC BLOCK
    //  Runs EXACTLY ONCE when the class is first loaded by the JVM.
    //  Executes BEFORE any constructor call.
    //  Use case: loading config, initializing static resources.
    // ══════════════════════════════════════════════════════════════════

    static {
        totalDriverCount = 0;
        System.out.println("[Static Block] Driver class loaded into JVM.");
    }

    // ══════════════════════════════════════════════════════════════════
    //  CONSTRUCTORS
    //
    //  Rules:
    //    - Same name as the class
    //    - No return type (not even void)
    //    - Called automatically by `new`
    //    - Can be overloaded (multiple constructors, different params)
    //    - If you write ANY constructor, Java stops providing the
    //      implicit no-arg constructor — write it explicitly if needed
    // ══════════════════════════════════════════════════════════════════

    /**
     * No-arg constructor.
     * All fields stay at Java defaults: 0, null, 0.0, false.
     */
    public Driver() {
        Driver.totalDriverCount++;
    }

    /**
     * Single-parameter constructor — sets name only.
     */
    public Driver(String name) {
        this.name = name;
        Driver.totalDriverCount++;
    }

    /**
     * Fully parameterized constructor.
     *
     * WHY `this` IS NEEDED:
     *   Without `this`, writing `driverId = driverId;` is a NO-OP.
     *   Java resolves BOTH sides to the parameter (nearest scope wins).
     *   The class field is never assigned. Object stays at defaults.
     *
     *   With `this`:
     *     this.driverId = driverId;
     *     ^^^^ class field    ^^^^ parameter
     *
     *   `this` refers to the CURRENT OBJECT being constructed.
     */
    public Driver(int driverId, String name, double rating, boolean isOnline) {
        this.driverId = driverId;
        this.name = name;
        this.rating = rating;
        this.isOnline = isOnline;
        Driver.totalDriverCount++;
    }

    // ══════════════════════════════════════════════════════════════════
    //  MEMBER FUNCTIONS (Instance Methods)
    // ══════════════════════════════════════════════════════════════════

    public void acceptRide(String rideId) {
        System.out.println("Ride accepted: " + rideId + " by " + name);
    }

    public void changeOnlineStatus() {
        isOnline = !isOnline;
        System.out.println(name + "'s status changed to: " + (isOnline ? "ONLINE" : "OFFLINE"));
    }

    // ══════════════════════════════════════════════════════════════════
    //  STATIC METHOD
    //  Belongs to the class, not to any object.
    //  Called via: Driver.register()
    //  CANNOT access instance variables or use `this`.
    // ══════════════════════════════════════════════════════════════════

    public static void register() {
        System.out.println("Driver registration service called.");
        System.out.println("Total drivers so far: " + Driver.totalDriverCount);
        // System.out.println(this.name);  // COMPILE ERROR — no `this` in static context
    }

    // ══════════════════════════════════════════════════════════════════
    //  toString() OVERRIDE
    //
    //  Default: "Driver@75bd9247" (ClassName @ identityHashCode)
    //  Override: return something meaningful for debugging.
    //
    //  @Override tells the compiler: "I'm intentionally replacing
    //  a method inherited from Object." (More in Inheritance.)
    // ══════════════════════════════════════════════════════════════════

    @Override
    public String toString() {
        return "Driver{id=" + driverId
                + ", name='" + name + "'"
                + ", rating=" + rating
                + ", online=" + isOnline + "}";
    }
}
