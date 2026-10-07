package com.scaler.LLD.Fundamentals.Class_3;

/**
 * LLD 3 — OOP-2: The Driver Class — Encapsulated
 *
 * EVOLUTION FROM CLASS 2:
 *   Class 2 Driver: all fields were package-private (no access control).
 *   Class 3 Driver: fields are PRIVATE + FINAL where appropriate.
 *
 * ENCAPSULATION IN ACTION:
 *   - All fields are private (data hiding).
 *   - driverId is final (assigned once in constructor, never changed).
 *   - rating has validation in its setter (0.0–5.0 only).
 *   - Getters provide read access; setters provide controlled write access.
 *   - No setter for driverId → it's read-only after construction.
 *
 * CONSTRUCTOR CHAINING:
 *   All constructors ultimately delegate to the full 3-param constructor
 *   via this(...). The driverId assignment and counter increment happen
 *   in EXACTLY ONE place — no duplication.
 *
 *   Driver()                    → this(null, 0.0, false)
 *   Driver(String)              → this(name, 0.0, false)
 *   Driver(String, double)      → this(name, rating, false)
 *   Driver(String, double, boolean) ← BASE — assigns driverId, increments count
 *   Driver(Driver)              → this(other.name, other.rating, other.isOnline)
 *
 * COPY CONSTRUCTOR:
 *   Creates a brand-new object with same field values BUT a fresh driverId.
 *   This makes logical sense: the copy is a distinct driver in the system,
 *   so it should have its own unique ID.
 */
public class Driver {

    // ══════════════════════════════════════════════════════════════════
    //  PRIVATE FIELDS — Encapsulated
    //  No one outside this class can directly read or write these.
    //  Access is controlled through getters/setters.
    // ══════════════════════════════════════════════════════════════════

    private final int driverId;    // final → assigned once, never changed
    private String name;
    private double rating;
    private boolean isOnline;

    // ══════════════════════════════════════════════════════════════════
    //  STATIC VARIABLE + BLOCK
    //  totalDriverCount belongs to the CLASS, not any object.
    //  Static block runs once when the class is first loaded.
    // ══════════════════════════════════════════════════════════════════

    static int totalDriverCount;

    static {
        totalDriverCount = 0;
        System.out.println("[Static Block] Driver class loaded into JVM.");
    }

    // ══════════════════════════════════════════════════════════════════
    //  CONSTRUCTORS — Properly Chained
    //
    //  The 3-param constructor is the BASE. It is the ONLY place that:
    //    1. Assigns driverId from totalDriverCount
    //    2. Increments totalDriverCount
    //
    //  Every other constructor delegates to it via this(...).
    //  This means if the ID-assignment logic ever changes, we only
    //  change it in ONE place. That's the whole point of chaining.
    //
    //  RULE: this() must be the FIRST statement in the constructor.
    //  WHY?  The delegated constructor must initialize the object
    //        before the current constructor does any work on it.
    // ══════════════════════════════════════════════════════════════════

    /**
     * BASE constructor — all other constructors chain here.
     * This is the SINGLE place where driverId is assigned and count is incremented.
     */
    public Driver(String name, double rating, boolean isOnline) {
        this.driverId = Driver.totalDriverCount;
        this.name = name;
        this.rating = rating;
        this.isOnline = isOnline;
        Driver.totalDriverCount++;
    }

    /** No-arg constructor → chains to base with defaults. */
    public Driver() {
        this(null, 0.0, false);
    }

    /** Name-only constructor → chains to base. */
    public Driver(String name) {
        this(name, 0.0, false);
    }

    /** Name + rating constructor → chains to base. */
    public Driver(String name, double rating) {
        this(name, rating, false);
    }

    /**
     * COPY CONSTRUCTOR — creates a brand-new object with same field values.
     *
     * Chains to the base constructor, so the copy gets a FRESH driverId.
     * This makes logical sense: the copy is a distinct driver entity,
     * so it should have its own unique ID — not a duplicate.
     *
     * This is a DEEP copy because:
     *   - int, double, boolean are primitives (always copied by value).
     *   - String is immutable (sharing the reference is safe).
     */
    public Driver(Driver other) {
        this(other.name, other.rating, other.isOnline);
        // driverId is auto-assigned by the base constructor — NOT copied from `other`.
        // The copy is a new driver with a new ID.
    }

    // ══════════════════════════════════════════════════════════════════
    //  BEHAVIORS
    // ══════════════════════════════════════════════════════════════════

    public void acceptRide(String rideId) {
        System.out.println("Ride accepted: " + rideId + " by " + name);
    }

    public void changeOnlineStatus() {
        isOnline = !isOnline;
        System.out.println(name + "'s status changed to: " + (isOnline ? "ONLINE" : "OFFLINE"));
    }

    public static void register() {
        System.out.println("Driver registration service called.");
        System.out.println("Total drivers so far: " + Driver.totalDriverCount);
    }

    // ══════════════════════════════════════════════════════════════════
    //  GETTERS — controlled read access
    //
    //  driverId has a getter but NO setter → read-only after construction.
    //  This is encapsulation: you control WHO can do WHAT.
    // ══════════════════════════════════════════════════════════════════

    public int getDriverId() { return driverId; }

    public String getName() { return name; }

    public double getRating() { return this.rating; }

    public boolean isOnline() { return isOnline; }

    // ══════════════════════════════════════════════════════════════════
    //  SETTERS — controlled write access with VALIDATION
    //
    //  setRating() validates the range. If rating is out of bounds,
    //  it throws an exception. The client can NEVER set an invalid rating.
    //
    //  No setDriverId() exists — driverId is final + read-only.
    // ══════════════════════════════════════════════════════════════════

    public void setName(String name) { this.name = name; }

    public void setRating(double rating) {
        if (rating < 0.0 || rating > 5.0) {
            throw new IllegalArgumentException("Rating must be between 0.0 and 5.0");
        }
        this.rating = rating;
    }

    public void setOnline(boolean online) { isOnline = online; }

    // ══════════════════════════════════════════════════════════════════
    //  toString() — meaningful output for debugging
    // ══════════════════════════════════════════════════════════════════

    @Override
    public String toString() {
        return "Driver{id=" + driverId
                + ", name='" + name + "'"
                + ", rating=" + rating
                + ", online=" + isOnline + "}";
    }
}
