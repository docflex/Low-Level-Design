package com.scaler.LLD.Module_2.Class_7.Decorator;

/** Decorator with coffee: base coffees (Espresso, Decaf) wrapped by any number of condiments. */
public class Client {
    static void print(String label, Coffee coffee) {
        System.out.println("  " + label + ": " + coffee.description() + " -> Rs." + coffee.cost());
    }

    public static void main(String[] args) {
        System.out.println("=== Base coffees ===");
        print("Plain espresso", new Espresso());
        print("Plain decaf", new Decaf());

        System.out.println("\n=== One condiment ===");
        print("Decaf + whip", new WhippedCream(new Decaf()));
        print("Espresso + mocha", new Mocha(new Espresso()));

        System.out.println("\n=== Adding condiments one at a time ===");
        Coffee coffee = new Espresso();
        coffee = new Mocha(coffee);
        print("step 1", coffee);
        coffee = new WhippedCream(coffee);
        print("step 2", coffee);
        coffee = new Mocha(coffee);
        print("step 3", coffee);

        System.out.println("\n=== The same order, built inline ===");
        Coffee inline = new Mocha(new WhippedCream(new Mocha(new Espresso())));
        print("inline", inline);
        System.out.println("  Total = 100 (Espresso) + 50 (Mocha) + 60 (Whip) + 50 (Mocha) = " + inline.cost());
    }
}
