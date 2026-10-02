package com.scaler.LLD.Fundamentals.Class_2;

/**
 * LLD 2 — OOP-1: Pass by Value in Java
 *
 * THE RULE: In Java, EVERYTHING is pass by value. No exceptions.
 *
 *   - Primitives: a COPY of the VALUE is passed.
 *   - Objects: a COPY of the REFERENCE (address) is passed.
 *
 * This means:
 *   - You CAN mutate an object's fields through the copied reference.
 *   - You CANNOT make the caller's variable point to a different object.
 *
 * SCRIPT CORRECTION (line 790):
 *   Original says: "Pass by Ref is only available in Languages
 *   where concept of pointers exist."
 *
 *   This is INCORRECT:
 *     - C++ has pass-by-reference (&) WITHOUT requiring raw pointers.
 *     - C# has `ref` and `out` keywords for pass-by-reference.
 *     - C has raw pointers but NO true pass-by-reference (it passes
 *       pointer VALUES — still pass by value!).
 *     - Java: pass by value ONLY.
 *
 *   Pass by reference is a LANGUAGE FEATURE, not a pointer feature.
 *
 * Run: Right-click → Run 'PassByValueDemo.main()'
 */
public class PassByValueDemo {

    // Simple inner class for demonstration
    static class Dog {
        String name;

        Dog(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Dog{name='" + name + "'}";
        }
    }


    public static void main(String[] args) {

        // ══════════════════════════════════════════════════════════════
        //  CASE 1: Primitives — Copy of the Value
        // ══════════════════════════════════════════════════════════════

        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║  Case 1: Primitives — Copy of the Value         ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        int number = 10;
        System.out.println("Before modifyPrimitive: number = " + number);   // 10
        modifyPrimitive(number);
        System.out.println("After modifyPrimitive:  number = " + number);   // 10 (unchanged!)

        //  Explanation:
        //  ┌──────────┐  copy   ┌──────────┐
        //  │ number=10 │ ────>  │   x=10   │ → x=50 (local copy only)
        //  └──────────┘        └──────────┘
        //  number is still 10. The method got a COPY of the int value.

        System.out.println("  → The method modified its LOCAL copy. Original unchanged.\n");


        // ══════════════════════════════════════════════════════════════
        //  CASE 2: Objects — Copy of the Reference (Mutation Visible)
        // ══════════════════════════════════════════════════════════════

        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║  Case 2: Objects — Mutation via Copied Reference ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        Dog myDog = new Dog("Buddy");
        System.out.println("Before modifyObject: myDog = " + myDog);   // Buddy
        modifyObject(myDog);
        System.out.println("After modifyObject:  myDog = " + myDog);   // Max (mutated!)

        //  Explanation:
        //  STACK                    HEAP
        //  ┌──────────────┐        ┌────────────┐
        //  │ myDog = 0xAAA┼──┐     │ Dog        │
        //  └──────────────┘  ├────>│ name="Max" │ ← mutated via d
        //  ┌──────────────┐  │     └────────────┘
        //  │ d = 0xAAA    ┼──┘     (same object!)
        //  └──────────────┘
        //
        //  Both `myDog` and `d` hold the SAME address.
        //  Changing d.name changes the shared heap object.

        System.out.println("  → Both refs point to the same heap object. Mutation is shared.\n");


        // ══════════════════════════════════════════════════════════════
        //  CASE 3: Objects — Reassignment (NOT Visible to Caller)
        // ══════════════════════════════════════════════════════════════

        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║  Case 3: Objects — Reassignment is LOCAL Only   ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        Dog separateDog = new Dog("Buddy");
        System.out.println("Before reassignObject: separateDog = " + separateDog);   // Buddy
        reassignObject(separateDog);
        System.out.println("After reassignObject:  separateDog = " + separateDog);   // Buddy (unchanged!)

        //  Explanation:
        //  STACK                     HEAP
        //  ┌──────────────────┐     ┌────────────────┐
        //  │ separateDog=0xBBB┼────>│ Dog("Buddy")   │ ← untouched
        //  └──────────────────┘     └────────────────┘
        //
        //  Inside the method:
        //  ┌──────────────────┐     ┌────────────────┐
        //  │ d = 0xCCC        ┼────>│ Dog("Charlie") │ ← only d sees this
        //  └──────────────────┘     └────────────────┘
        //
        //  d = new Dog("Fido") overwrites the LOCAL copy of the reference.
        //  separateDog in main() still points to the original Dog("Buddy").

        System.out.println("  → Reassigning the parameter only changes the LOCAL copy.");
        System.out.println("  → The caller's variable still points to the original object.\n");


        // ══════════════════════════════════════════════════════════════
        //  SUMMARY
        // ══════════════════════════════════════════════════════════════

        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║  Summary                                         ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.println("  Java is ALWAYS pass by value.");
        System.out.println("  Primitives: copy of value       → original unchanged.");
        System.out.println("  Objects:    copy of reference    → can mutate, can't reassign caller's ref.");
        System.out.println("  True pass-by-reference (C++ &, C# ref): method can change what the");
        System.out.println("  caller's variable points to. Java does NOT support this.");
    }


    // ── Helper Methods ──

    // Copies the primitive value. Changes stay inside this scope.
    public static void modifyPrimitive(int x) {
        x = 50;   // Only the local copy is changed
    }

    // Copies the reference. Both refs point to the same heap object.
    public static void modifyObject(Dog d) {
        d.name = "Max";   // Modifies the actual object on the heap
    }

    // Copies the reference. Reassigning `d` only changes the LOCAL copy.
    public static void reassignObject(Dog d) {
        d = new Dog("Fido");     // d now points to a brand-new object
        d.name = "Charlie";      // Changes the new Dog, NOT the caller's Dog
    }
}
