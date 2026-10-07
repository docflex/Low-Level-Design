package com.scaler.LLD.Fundamentals.Class_3;

/**
 * LLD 3 — OOP-2: Route — Used in Deep vs Shallow Copy Demo
 *
 * A Route represents a journey from source to destination.
 * Used as a NESTED OBJECT inside Fare to demonstrate
 * the shallow vs deep copy problem.
 *
 * ENCAPSULATED: Fields are private with getters/setters,
 * consistent with the Class 3 encapsulation lesson.
 *
 * KEY POINT:
 *   When Fare's copy constructor does `this.route = other.route`,
 *   both Fare objects share the SAME Route reference (shallow copy).
 *   To deep-copy, Fare must do `this.route = new Route(other.route)`,
 *   which calls Route's own copy constructor.
 */
public class Route {
    private int routeId;
    private String source;
    private String destination;

    /** Normal constructor. */
    public Route(int routeId, String source, String destination) {
        this.routeId = routeId;
        this.source = source;
        this.destination = destination;
    }

    /**
     * Copy constructor — creates a new Route with the same values.
     *
     * String is immutable, so we can safely share the reference.
     * No need to do `new String(other.source)` — that would be wasteful.
     */
    public Route(Route other) {
        this.routeId = other.routeId;
        this.source = other.source;
        this.destination = other.destination;
    }

    // ── Getters & Setters ──

    public int getRouteId() { return routeId; }

    public String getSource() { return source; }

    public void setSource(String source) { this.source = source; }

    public String getDestination() { return destination; }

    public void setDestination(String destination) { this.destination = destination; }

    @Override
    public String toString() {
        return "Route{id=" + routeId
                + ", " + source + " -> " + destination + "}";
    }
}
