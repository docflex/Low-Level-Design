package com.scaler.LLD.Fundamentals.Class_5;

/**
 * LLD 5 — Lab: User — Abstract Base Class
 *
 * Milestone A, Tasks 1–3 & 6:
 *   - Encapsulation: private fields + getters/setters
 *   - Abstract class: no "User" object in reality (it's a concept)
 *   - Static: totalUsers tracks count across all instances
 *   - Final: userId is assigned once at construction and never changes
 *   - Constructor chaining: all constructors delegate to the base
 *     constructor so ID generation + counter increment happen in ONE place.
 *
 * FIX APPLIED — Constructor Chaining:
 *   Original code duplicated generateUniqueId() + totalUsers++ in all
 *   3 constructors. This is the same anti-pattern we fixed in LLD 3's
 *   Driver class. Now all constructors chain to User(String, String).
 *
 * FIX APPLIED — Missing getUserId():
 *   userId was private final but had no getter. Added.
 *
 * FIX APPLIED — Missing toString():
 *   Printing a User showed "Member@1a2b3c". Added toString().
 */
public abstract class User {
    // ATTRIBUTES

    private final String userId;
    private String name;
    private String contactInfo;

    private static int totalUsers = 0;
    private static int idCounter = 0;


    // GETTER SETTERS

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    // CONSTRUCTORS — chained to the parameterized constructor (base)

    // BASE — the ONLY place that assigns userId and increments totalUsers
    public User(String name, String contactInfo) {
        this.userId = generateUniqueId();
        this.name = name;
        this.contactInfo = contactInfo;
        User.totalUsers++;
    }

    public User()            { this(null, null); }
    public User(User other)  { this(other.name, other.contactInfo); }

    // Behaviors

    abstract void displayDashboard();

    abstract boolean canBorrowBooks();


    // Utility Methods

    private static String generateUniqueId() {
        String uniqueId = "U-" + User.idCounter;
        User.idCounter++;
        return uniqueId;
    }

    // Static Access Methods

    public static int getTotalUsers() {
        return User.totalUsers;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{id='" + userId
                + "', name='" + name + "'}";
    }
}
