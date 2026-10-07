package com.scaler.LLD.Fundamentals.Class_4.overloading;

/**
 * LLD 4 — OOP-3: Calculator — Compile-Time Polymorphism (Method Overloading)
 *
 * METHOD OVERLOADING RULES:
 *   Methods share the SAME NAME but differ in their SIGNATURE.
 *   Signature = method name + parameter types + parameter order + parameter count.
 *
 *   What DOES NOT count as a different signature:
 *     - Different parameter NAMES (int a vs int x)
 *     - Different RETURN TYPES
 *     - Different ACCESS MODIFIERS
 *
 * SCRIPT CORRECTION:
 *   The original script had addNumbers(double, int) returning int.
 *   double + int → double in Java (widening: int → long → float → double).
 *   Returning int from a double expression won't compile without an explicit cast.
 *   Fixed: return type is now double.
 */
public class Calculator {

    // Overload 1: two ints
    public int addNumbers(int num1, int num2) {
        return num1 + num2;
    }

    // Overload 2: three ints (different parameter COUNT)
    public int addNumbers(int num1, int num2, int num3) {
        return num1 + num2 + num3;
    }

    // Overload 3: double + int (different parameter TYPES)
    // FIXED: returns double, not int.
    // In Java, double + int → double (widening conversion).
    public double addNumbers(double num1, int num2) {
        return num1 + num2;
    }
}
