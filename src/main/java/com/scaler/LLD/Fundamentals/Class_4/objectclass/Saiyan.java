package com.scaler.LLD.Fundamentals.Class_4.objectclass;

import java.util.Objects;

/**
 * LLD 4 — OOP-3: Saiyan — Object Class Demo (toString, equals, hashCode)
 *
 * Every Java class implicitly extends Object. Object provides:
 *   - toString()  → default: ClassName@hexHashCode (e.g., "Saiyan@1a2b3c")
 *   - equals()    → default: compares REFERENCES (same as ==)
 *   - hashCode()  → default: derived from memory address
 *
 * We override all three to demonstrate custom equality.
 *
 * CONTRACT: equals() and hashCode() MUST be overridden together.
 *   If two objects are .equals(), they MUST have the same hashCode().
 *   Otherwise HashMap/HashSet lookups break.
 *
 * SCRIPT CORRECTION: The script spells "Sayan" — the correct
 * Dragon Ball Z spelling is "Saiyan."
 */
public class Saiyan {

    private final int saiyanId;
    private final String name;

    public Saiyan(int saiyanId, String name) {
        this.saiyanId = saiyanId;
        this.name = name;
    }

    public int getSaiyanId() { return saiyanId; }
    public String getName() { return name; }

    // ── toString: human-readable representation ──
    @Override
    public String toString() {
        return "Saiyan{id=" + saiyanId + ", name='" + name + "'}";
    }

    // ── equals: two Saiyans are "equal" if they have the same saiyanId ──
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;                          // same reference
        if (o == null || getClass() != o.getClass()) return false;   // null or wrong type
        Saiyan saiyan = (Saiyan) o;
        return saiyanId == saiyan.saiyanId;                  // ID-based equality
    }

    // ── hashCode: MUST be consistent with equals ──
    // If equals() uses saiyanId, hashCode() must also use saiyanId.
    @Override
    public int hashCode() {
        return Objects.hash(saiyanId);
    }
}
