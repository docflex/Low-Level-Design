package com.scaler.LLD.Fundamentals.Class_1;

/**
 * LLD 1 — Intro to LLD: Procedural vs Object-Oriented Programming
 *
 * This demo contrasts the two paradigms using a Student entity.
 *
 * PROCEDURAL STYLE:
 *   - Data and functions are separate.
 *   - Functions operate on raw data (arrays, primitives).
 *   - Any function can modify anything — no ownership.
 *   - With 100+ global functions, debugging is a nightmare
 *     (who changed what? trace every function).
 *
 * OOP STYLE:
 *   - Data and behavior live together inside objects.
 *   - Each object OWNS its data and controls access to it.
 *   - The "control flip": instead of functions pulling data,
 *     objects expose behaviors that internally manage their own data.
 *   - With 100 functions spread across 10 classes (10 each),
 *     debugging is scoped — you know WHERE to look.
 *
 * KEY INSIGHT (from LLD 1):
 *   88% of engineering time is spent READING and MAINTAINING code.
 *   Only 12% is writing new code.
 *   OOP makes the 88% easier.
 *
 * Run: Right-click → Run 'ProceduralVsOOP.main()'
 */
public class ProceduralVsOOP {

    // ═══════════════════════════════════════════════════════════════════
    //  PROCEDURAL APPROACH — data and functions are separate
    // ═══════════════════════════════════════════════════════════════════

    // "Data" — just raw arrays. No protection, no structure.
    static String[] names = new String[10];
    static String[] emails = new String[10];
    static int[] gradYears = new int[10];
    static int studentCount = 0;

    // "Functions" — operate on the raw arrays directly
    static void addStudentProcedural(String name, String email, int gradYear) {
        names[studentCount] = name;
        emails[studentCount] = email;
        gradYears[studentCount] = gradYear;
        studentCount++;
    }

    static void printStudentProcedural(int index) {
        // Anyone can call this with any index — no bounds check inherent in the design.
        // Anyone can also directly modify names[0] = "HACKED" — no protection.
        System.out.println("  [Procedural] " + names[index]
                + " | " + emails[index]
                + " | Grad: " + gradYears[index]);
    }


    // ═══════════════════════════════════════════════════════════════════
    //  OOP APPROACH — data and behavior live together
    // ═══════════════════════════════════════════════════════════════════

    // See Student.java for the full OOP version.
    // The Student class:
    //   - OWNS its data (name, email, gradYear are private)
    //   - Controls access via methods (getName(), setEmail(), etc.)
    //   - Validates on construction (gradYear must be reasonable)
    //   - Has a meaningful toString() for debugging


    // ═══════════════════════════════════════════════════════════════════
    //  THE CONTROL FLIP
    // ═══════════════════════════════════════════════════════════════════
    //
    //  PROCEDURAL:  function(data)     →  function pulls data, operates on it
    //  OOP:         object.behavior()  →  object owns data, exposes behavior
    //
    //  Analogy: Ordering food
    //    Procedural: You go into the kitchen, grab ingredients, cook yourself.
    //    OOP:        You tell the waiter "I want pasta." The kitchen handles it.
    //
    //  ┌──────────────────────────────┐   ┌──────────────────────────────┐
    //  │     PROCEDURAL               │   │          OOP                 │
    //  │                              │   │                              │
    //  │  function1(data)             │   │  ┌────────────────────┐     │
    //  │  function2(data)             │   │  │ Object             │     │
    //  │  function3(data)             │   │  │  - private data    │     │
    //  │  ...                         │   │  │  + behavior1()     │     │
    //  │  function100(data)           │   │  │  + behavior2()     │     │
    //  │                              │   │  └────────────────────┘     │
    //  │  Bug? Check ALL 100          │   │  Bug? Check the 1 class    │
    //  │  functions.                  │   │  that owns the data.       │
    //  └──────────────────────────────┘   └──────────────────────────────┘


    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║  LLD 1 — Procedural vs OOP Demo                ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        // ── Procedural Demo ──
        System.out.println("\n── PROCEDURAL STYLE ──");
        addStudentProcedural("Alice", "alice@scaler.com", 2024);
        addStudentProcedural("Bob", "bob@scaler.com", 2023);
        printStudentProcedural(0);
        printStudentProcedural(1);

        // Problem: anyone can directly corrupt the data
        names[0] = "CORRUPTED";
        System.out.println("\n  After names[0] = \"CORRUPTED\":");
        printStudentProcedural(0);   // No protection!

        // ── OOP Demo ──
        System.out.println("\n── OOP STYLE ──");
        Student s1 = new Student("Alice", "alice@scaler.com", 2024, "Google");
        Student s2 = new Student("Bob", "bob@scaler.com", 2023, "Amazon");

        System.out.println("  " + s1);
        System.out.println("  " + s2);

        // Data is protected — you must go through the object's methods.
        // s1.name = "CORRUPTED";     // COMPILE ERROR if fields are private
        s1.setEmail("alice.new@scaler.com");   // Controlled mutation
        System.out.println("\n  After s1.setEmail(\"alice.new@scaler.com\"):");
        System.out.println("  " + s1);

        // Validation built into the object
        System.out.println("\n  Attempting invalid grad year (1800):");
        Student s3 = new Student("Charlie", "charlie@scaler.com", 1800, "Meta");
        System.out.println("  " + s3);   // gradYear clamped/defaulted by validation

        System.out.println("\n── KEY TAKEAWAY ──");
        System.out.println("  Procedural: data is exposed, any function can corrupt it.");
        System.out.println("  OOP: objects own and protect their data via encapsulation.");
    }
}
