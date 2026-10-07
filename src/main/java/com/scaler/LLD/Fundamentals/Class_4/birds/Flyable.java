package com.scaler.LLD.Fundamentals.Class_4.birds;

/**
 * LLD 4 — OOP-3: Flyable — Behavior Interface
 *
 * NOT all birds can fly (Penguin, Dodo).
 * Making fly() an interface instead of putting it in Bird prevents
 * non-flying birds from being forced to implement a meaningless fly().
 */
public interface Flyable {
    void fly();
}
