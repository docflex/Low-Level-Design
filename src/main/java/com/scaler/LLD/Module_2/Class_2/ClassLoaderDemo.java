package com.scaler.LLD.Module_2.Class_2;

/**
 * When does a static block run?
 * Not at program start: on the class's FIRST active use (new, static method, static field),
 * and only ONCE per class loader -- no matter how many objects are created afterwards.
 * Output: A, Person Class Has Been Loaded, Constructor (Rehber), B, Constructor (Second), C
 */
public class ClassLoaderDemo {
    public static void main(String[] args) {
        System.out.println("A");
        Person person = new Person("Rehber");      // first use -> static block runs now
        System.out.println("B");
        Person second = new Person("Second");      // static block does NOT run again
        System.out.println("C");
        person.sayHello();
        second.sayHello();
    }
}
