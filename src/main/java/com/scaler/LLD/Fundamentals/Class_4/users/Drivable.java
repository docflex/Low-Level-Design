package com.scaler.LLD.Fundamentals.Class_4.users;

/**
 * LLD 4 — OOP-3: Drivable — Interface (CAN-DO behavior contract)
 *
 * WHY AN INTERFACE, NOT A METHOD IN HumanUser?
 *   acceptRide() only applies to Drivers, not Customers.
 *   Putting it in HumanUser would force Customer to have it too.
 *
 * WHY NOT A METHOD IN Driver?
 *   RoboDriver also needs acceptRide() but is NOT a HumanUser.
 *   An interface lets both Driver and RoboDriver share the contract
 *   without forcing RoboDriver into the HumanUser hierarchy.
 *
 * NAMING:
 *   Interfaces model verbs / capabilities (CAN-DO):
 *     Driver CAN Drive    (grammatically correct)
 *     Driver IS A Drivable (sounds wrong)
 *   Abstract classes model nouns (IS-A):
 *     Driver IS A User
 *
 * INTERFACE FACTS:
 *   - All methods are implicitly public and abstract (pre-Java 8).
 *   - Since Java 8: interfaces can also have 'default' and 'static' methods.
 *   - Since Java 9: interfaces can have 'private' helper methods.
 *   - Interfaces CAN have fields — they are implicitly public static final (constants).
 *
 *   SCRIPT CORRECTION: The script says "Interfaces don't have variables,
 *   all methods are abstract." Both claims are incomplete — see above.
 */
public interface Drivable {

    void acceptRide();

    void completeRide();
}
