package com.scaler.LLD.Fundamentals.Class_4.overloading;

/**
 * LLD 4 — OOP-3: Compile-Time Polymorphism (Method Overloading) Demo
 *
 * TEACHING FLOW:
 *   1. Show 3 overloaded addNumbers() methods in Calculator.
 *   2. Demonstrate how the compiler picks the right overload at compile time.
 *   3. Walk through 3 quiz questions from the script.
 *
 * WHY "COMPILE TIME"?
 *   The decision of WHICH overloaded method to call is resolved by the
 *   compiler based on the argument types at the call site. If no match
 *   exists → compile error (you don't even get to run the program).
 *
 * Run: Right-click → Run 'OverloadingDemo.main()'
 */
public class OverloadingDemo {

    public static void main(String[] args) {

        Calculator c = new Calculator();

        // ══════════════════════════════════════════════════════════════
        //  DEMO 1: Compiler Picks the Right Overload
        // ══════════════════════════════════════════════════════════════

        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║  DEMO 1: Method Overloading — Basic Calls       ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        // Matches: addNumbers(int, int)
        System.out.println("c.addNumbers(3, 4)       = " + c.addNumbers(3, 4));

        // Matches: addNumbers(int, int, int)
        System.out.println("c.addNumbers(2, 4, 5)    = " + c.addNumbers(2, 4, 5));

        // Matches: addNumbers(double, int)
        System.out.println("c.addNumbers(2.0, 5)     = " + c.addNumbers(2.0, 5));

        // c.addNumbers(2.0, 3.0) → COMPILE ERROR!
        // No overload takes (double, double). Closest is (double, int) but
        // 3.0 is a double and Java won't NARROW double → int automatically.
        // Uncomment to see the error:
        // System.out.println(c.addNumbers(2.0, 3.0));

        // c.addNumbers(1, 2, 3, 4) → COMPILE ERROR!
        // No overload takes 4 parameters.
        // Uncomment to see the error:
        // System.out.println(c.addNumbers(1, 2, 3, 4));


        // ══════════════════════════════════════════════════════════════
        //  QUIZ Q1: Same name, same parameter types, different names
        // ══════════════════════════════════════════════════════════════

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  QUIZ Q1: add(int a, int b) vs add(int x, int y)║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        System.out.println("Q: Is this valid overloading?");
        System.out.println("   add(int a, int b)");
        System.out.println("   add(int x, int y)");
        System.out.println();
        System.out.println("A: NO — won't even compile.");
        System.out.println("   Parameter NAMES don't matter. Both take (int, int).");
        System.out.println("   The compiler sees identical signatures → ambiguity error.");


        // ══════════════════════════════════════════════════════════════
        //  QUIZ Q2: Same parameters, different return type
        // ══════════════════════════════════════════════════════════════

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  QUIZ Q2: int add(int,int) vs double add(int,int)║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        System.out.println("Q: Is this valid overloading?");
        System.out.println("   int    add(int a, int b)");
        System.out.println("   double add(int a, int b)");
        System.out.println();
        System.out.println("A: NO — return type is NOT part of the method signature.");
        System.out.println("   Why? The caller might ignore the return value:");
        System.out.println("     c.add(2, 3);    // no assignment — which one to call?");
        System.out.println("   The compiler can't disambiguate → compile error.");
        System.out.println();
        System.out.println("   SCRIPT CORRECTION: The script says 'return happens at");
        System.out.println("   runtime so Java can't decide.' The real reason is simpler:");
        System.out.println("   the return type isn't part of the signature because the");
        System.out.println("   compiler resolves overloads using ONLY name + param types.");


        // ══════════════════════════════════════════════════════════════
        //  QUIZ Q3: Same name, different parameter ORDER
        // ══════════════════════════════════════════════════════════════

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  QUIZ Q3: add(int,double) vs add(double,int)    ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        System.out.println("Q: Is this valid overloading?");
        System.out.println("   double add(int a, double b)");
        System.out.println("   double add(double a, int b)");
        System.out.println();
        System.out.println("A: YES — parameter ORDER differs.");
        System.out.println("   c.add(5.0, 3) → matches add(double, int)");
        System.out.println("   c.add(3, 5.0) → matches add(int, double)");


        // ══════════════════════════════════════════════════════════════
        //  SUMMARY: What Counts for Overloading
        // ══════════════════════════════════════════════════════════════

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  Overloading Signature Rules                    ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        System.out.println("  Counts:     Number of parameters");
        System.out.println("  Counts:     Data types of parameters");
        System.out.println("  Counts:     Order of parameter types");
        System.out.println("  Ignored:    Parameter names");
        System.out.println("  Ignored:    Return type (not part of signature)");
        System.out.println("  Ignored:    Access modifier (not part of signature)");
        System.out.println();
        System.out.println("  Widening chain: int -> long -> float -> double");
        System.out.println("  Java will auto-WIDEN (int -> double) but never auto-NARROW.");
    }
}
