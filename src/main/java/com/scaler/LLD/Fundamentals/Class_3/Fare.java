package com.scaler.LLD.Fundamentals.Class_3;

/**
 * LLD 3 — OOP-2: Fare — Demonstrates Shallow vs Deep Copy Trap
 *
 * A Fare has primitives (fareId, amount) and a NESTED OBJECT (Route).
 *
 * ENCAPSULATED: Fields are private with getters/setters,
 * consistent with the Class 3 encapsulation lesson.
 *
 * THE TRAP:
 *   Primitives are always deeply copied (they're values, not references).
 *   But `this.route = other.route` copies the REFERENCE, not the object.
 *   Both Fare copies then share the same Route on the heap.
 *
 * THE FIX:
 *   `this.route = new Route(other.route)` — calls Route's copy constructor
 *   to create a brand-new Route object for the copy.
 *
 * COPY RULES:
 *   Primitives (int, double)  → automatic deep copy (by value).
 *   Immutables (String)       → don't need copying (can't be mutated).
 *   Custom objects (Route)    → MUST be manually deep-copied.
 */
public class Fare {
    private int fareId;
    private double amount;
    private Route route;

    /** Normal constructor. */
    public Fare(int fareId, double amount, Route route) {
        this.fareId = fareId;
        this.amount = amount;
        this.route = route;
    }

    /**
     * Copy constructor — DEEP COPY.
     *
     * fareId, amount: primitives → automatically deep-copied.
     * route: custom object → must create new Route(other.route).
     *
     * If we had written `this.route = other.route` instead,
     * it would be a SHALLOW copy — both Fare objects would
     * share the same Route, and changing one would affect the other.
     */
    public Fare(Fare other) {
        this.fareId = other.fareId;
        this.amount = other.amount;
        this.route = new Route(other.route);   // DEEP copy of nested object
    }

    // ── Getters & Setters ──

    public int getFareId() { return fareId; }

    public double getAmount() { return amount; }

    public void setAmount(double amount) { this.amount = amount; }

    public Route getRoute() { return route; }

    @Override
    public String toString() {
        return "Fare{id=" + fareId
                + ", amount=" + amount
                + ", route=" + route + "}";
    }
}
