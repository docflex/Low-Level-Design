package com.scaler.LLD.Fundamentals.Class_4.users;

/**
 * LLD 4 — OOP-3: HumanUser — Abstract Class (IS-A relationship)
 *
 * In Uber, both Driver and Customer share common attributes (name, age)
 * and behaviors (login). But there's no real-world "User" object —
 * you're always either a Driver or a Customer. So this class is abstract.
 *
 * Abstract Classes model nouns (IS-A):
 *   Driver IS A HumanUser
 *   Customer IS A HumanUser
 */
public abstract class HumanUser {

    String name;
    int age;

    public void login() {
        System.out.println(name + " logged in");
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{name='" + name + "', age=" + age + "}";
    }
}
