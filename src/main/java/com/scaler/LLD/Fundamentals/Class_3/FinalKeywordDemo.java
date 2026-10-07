package com.scaler.LLD.Fundamentals.Class_3;

/**
 * LLD 3 — OOP-2: Final Keyword Demo
 *
 * `final` achieves IMMUTABILITY at three levels:
 *
 *   1. Final VARIABLE  → cannot be reassigned after initialization.
 *   2. Final METHOD    → cannot be overridden by subclasses.
 *   3. Final CLASS     → cannot be extended (no subclasses allowed).
 *
 * PPT CORRECTION (Slide 21):
 *   The PPT says:
 *     "Final Variables -> Cannot be modified."     ← Correct
 *     "Final Method -> Cannot be Overridden."      ← Correct
 *     "Final Variables -> Cannot be Inherited."    ← WRONG (copy-paste error)
 *
 *   Should be: "Final CLASSES -> Cannot be Inherited (extended)."
 *   Final variables CAN be inherited — they just can't be reassigned.
 *
 * Run: Right-click → Run 'FinalKeywordDemo.main()'
 */
public class FinalKeywordDemo {

    // ══════════════════════════════════════════════════════════════════
    //  1. FINAL VARIABLES — Cannot be reassigned
    // ══════════════════════════════════════════════════════════════════

    static class Employee {
        final int employeeId;     // must be assigned in constructor, then locked
        String name;

        Employee(int id, String name) {
            this.employeeId = id;   // assigned ONCE — OK
            this.name = name;
        }

        void tryToChangeId() {
            // this.employeeId = 999;  // COMPILE ERROR: cannot assign to final variable
            System.out.println("  Cannot change employeeId — it's final!");
        }
    }

    // ══════════════════════════════════════════════════════════════════
    //  2. FINAL METHODS — Cannot be overridden
    // ══════════════════════════════════════════════════════════════════

    static class PaymentProcessor {
        /**
         * This method is FINAL — subclasses must NOT change how
         * payments are validated. This is a security guarantee.
         */
        public final void validatePayment(double amount) {
            System.out.println("  Validating payment of $" + amount + " (FINAL — tamper-proof)");
        }

        /** Non-final — subclasses CAN customize how they process. */
        public void processPayment(double amount) {
            System.out.println("  Processing payment of $" + amount + " (base)");
        }
    }

    static class CreditCardProcessor extends PaymentProcessor {
        // Cannot override validatePayment — it's final!
        // public void validatePayment(double amount) { }  // COMPILE ERROR

        // CAN override processPayment — it's not final
        @Override
        public void processPayment(double amount) {
            System.out.println("  Processing CREDIT CARD payment of $" + amount);
        }
    }

    // ══════════════════════════════════════════════════════════════════
    //  3. FINAL CLASSES — Cannot be extended
    // ══════════════════════════════════════════════════════════════════

    /** This class is final — no one can create a subclass of it. */
    static final class Constants {
        static final double PI = 3.14159;
        static final String APP_NAME = "Scaler LLD";

        // Private constructor — utility class, no instances needed
        private Constants() { }
    }

    // class ExtendedConstants extends Constants { }  // COMPILE ERROR: cannot extend final class

    // Real-world examples of final classes in Java:
    //   java.lang.String    — final
    //   java.lang.Integer   — final
    //   java.lang.Math      — final


    // ══════════════════════════════════════════════════════════════════
    //  BONUS: Final variables CAN be inherited
    //  (contradicting the PPT's copy-paste error)
    // ══════════════════════════════════════════════════════════════════

    static class Animal {
        protected final int legs;   // protected + final — accessible by subclasses, never reassigned

        Animal(int legs) {
            this.legs = legs;
        }
    }

    static class Dog extends Animal {
        String breed;

        Dog(String breed) {
            super(4);          // assigns legs = 4 in Animal constructor
            this.breed = breed;
        }

        void describe() {
            // legs IS accessible — it's protected (visible to subclasses) AND final (immutable)
            System.out.println("  " + breed + " has " + legs + " legs (protected final, inherited)");
        }
    }


    // ══════════════════════════════════════════════════════════════════
    //  MAIN
    // ══════════════════════════════════════════════════════════════════

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║  Final Keyword Demo                             ║");
        System.out.println("╚══════════════════════════════════════════════════╝");


        // ── 1. Final Variables ──
        System.out.println("\n── 1. Final Variables ──");
        Employee emp = new Employee(1001, "Alice");
        System.out.println("  Employee ID: " + emp.employeeId + " (cannot be changed)");
        emp.tryToChangeId();
        emp.name = "Bob";   // name is NOT final — can be changed
        System.out.println("  Name changed to: " + emp.name + " (non-final, mutable)");


        // ── 2. Final Methods ──
        System.out.println("\n── 2. Final Methods ──");
        CreditCardProcessor cc = new CreditCardProcessor();
        cc.validatePayment(100.0);    // final method from parent — can't be overridden
        cc.processPayment(100.0);     // overridden method — credit card version


        // ── 3. Final Classes ──
        System.out.println("\n── 3. Final Classes ──");
        System.out.println("  Constants.PI       = " + Constants.PI);
        System.out.println("  Constants.APP_NAME = " + Constants.APP_NAME);
        System.out.println("  Constants class is final — cannot be extended.");
        System.out.println("  (Same as String, Integer, Math in Java.)");


        // ── 4. Proof: Final Variables ARE Inherited ──
        System.out.println("\n── 4. Final Variables ARE Inherited ──");
        Dog d = new Dog("Golden Retriever");
        d.describe();   // accesses `legs` — a final field from Animal
        System.out.println("  PPT says final vars can't be inherited — WRONG.");
        System.out.println("  Final CLASSES can't be inherited. Final vars can.");
    }
}
