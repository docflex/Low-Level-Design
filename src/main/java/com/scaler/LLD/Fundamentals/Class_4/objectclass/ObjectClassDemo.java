package com.scaler.LLD.Fundamentals.Class_4.objectclass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * LLD 4 — OOP-3: Object Class Demo — toString, equals, hashCode
 *
 * TEACHING FLOW:
 *   1. toString() — default prints hex address; override for readable output.
 *   2. == vs .equals() — both compare references by DEFAULT.
 *   3. Override equals() — now logical equality works.
 *   4. hashCode() contract — must override together with equals().
 *   5. HashMap demo — shows WHY the contract matters.
 *
 * Run: Right-click → Run 'ObjectClassDemo.main()'
 */
public class ObjectClassDemo {

    public static void main(String[] args) {

        Saiyan goku   = new Saiyan(1, "Goku");
        Saiyan vegeta  = new Saiyan(1, "Goku");   // same id & name, different object

        // ══════════════════════════════════════════════════════════════
        //  DEMO 1: toString()
        // ══════════════════════════════════════════════════════════════

        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║  DEMO 1: toString()                            ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        // Without our override, this would print: Saiyan@1a2b3c (hex address)
        // With our override, it prints: Saiyan{id=1, name='Goku'}
        System.out.println("goku   = " + goku);
        System.out.println("vegeta = " + vegeta);


        // ══════════════════════════════════════════════════════════════
        //  DEMO 2: == vs .equals()
        // ══════════════════════════════════════════════════════════════

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  DEMO 2: == vs .equals()                       ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        System.out.println("goku == vegeta      : " + (goku == vegeta));
        // false — different objects on the heap (different addresses)

        System.out.println("goku.equals(vegeta) : " + goku.equals(vegeta));
        // true — our equals() override checks saiyanId, and both have id=1

        System.out.println();
        System.out.println("==        -> always compares REFERENCES (addresses)");
        System.out.println(".equals() -> default also compares references,");
        System.out.println("            but we OVERRODE it to compare saiyanId.");


        // ══════════════════════════════════════════════════════════════
        //  DEMO 3: hashCode Contract
        // ══════════════════════════════════════════════════════════════

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  DEMO 3: hashCode() Contract                  ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        System.out.println("goku.hashCode()   = " + goku.hashCode());
        System.out.println("vegeta.hashCode() = " + vegeta.hashCode());
        System.out.println("Same hashCode? " + (goku.hashCode() == vegeta.hashCode()));

        System.out.println();
        System.out.println("CONTRACT: If a.equals(b) is true, then");
        System.out.println("          a.hashCode() MUST equal b.hashCode().");
        System.out.println();
        System.out.println("If you override equals() but NOT hashCode(),");
        System.out.println("HashMap/HashSet will break — see Demo 4.");


        // ══════════════════════════════════════════════════════════════
        //  DEMO 4: HashMap Depends on Both equals() and hashCode()
        // ══════════════════════════════════════════════════════════════

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  DEMO 4: HashMap — Why hashCode() Matters     ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        Map<Saiyan, List<String>> techniques = new HashMap<>();
        techniques.put(goku, List.of("Kamehameha", "Spirit Bomb"));

        // Look up using the SAME object — always works
        System.out.println("techniques.get(goku)   = " + techniques.get(goku));

        // Look up using a DIFFERENT object with same id — works because
        // both equals() and hashCode() are correctly overridden.
        System.out.println("techniques.get(vegeta) = " + techniques.get(vegeta));

        System.out.println();
        System.out.println("vegeta has id=1, same as goku.");
        System.out.println("HashMap: hashCode(vegeta) -> same bucket -> equals() -> match -> found!");
        System.out.println();
        System.out.println("If hashCode() were NOT overridden:");
        System.out.println("  hashCode(vegeta) -> different bucket -> never finds the entry -> null!");
        System.out.println("  The map would return null even though goku.equals(vegeta) is true.");


        // ══════════════════════════════════════════════════════════════
        //  SUMMARY: Object Class Methods
        // ══════════════════════════════════════════════════════════════

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  Object Class — Summary                        ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        System.out.println("  Every class extends Object implicitly.");
        System.out.println();
        System.out.println("  toString():");
        System.out.println("    Default: ClassName@hex  -> Override for human-readable output.");
        System.out.println();
        System.out.println("  equals():");
        System.out.println("    Default: same as ==     -> Override for logical equality.");
        System.out.println();
        System.out.println("  hashCode():");
        System.out.println("    Default: from address   -> ALWAYS override with equals().");
        System.out.println();
        System.out.println("  GOLDEN RULE: Override equals() and hashCode() TOGETHER. Always.");
    }
}
