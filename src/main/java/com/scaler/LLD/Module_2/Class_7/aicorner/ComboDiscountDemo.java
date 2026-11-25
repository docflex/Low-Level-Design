package com.scaler.LLD.Module_2.Class_7.aicorner;

import com.scaler.LLD.Module_2.Class_7.aicorner.Drinks.ComboDiscount;
import com.scaler.LLD.Module_2.Class_7.aicorner.Drinks.Espresso;
import com.scaler.LLD.Module_2.Class_7.aicorner.Drinks.Mocha;
import com.scaler.LLD.Module_2.Class_7.aicorner.Drinks.Mochaccino;
import com.scaler.LLD.Module_2.Class_7.aicorner.Drinks.Whip;

/**
 * AI Corner: "if the order has both Mocha and Whip, apply a 10% discount on the total".
 * Compares a type-based check (walk the decorator chain) with a description-based one.
 */
public class ComboDiscountDemo {

    /** What an AI often suggests: look for words in the description. */
    static boolean comboByDescription(Beverage beverage) {
        String d = beverage.description();
        return d.contains("Mocha") && d.contains("Whip");
    }

    static void show(String label, Beverage beverage) {
        Beverage priced = new ComboDiscount(beverage);
        System.out.println("  " + label);
        System.out.println("    " + priced.description() + " -> Rs." + priced.cost());
        System.out.println("    combo by type: " + (beverage.contains(Mocha.class) && beverage.contains(Whip.class))
                + ", combo by description: " + comboByDescription(beverage));
    }

    public static void main(String[] args) {
        System.out.println("=== Combo discount wrapped outermost ===");
        show("Espresso + Mocha + Whip", new Whip(new Mocha(new Espresso())));
        show("Espresso + Whip + Mocha (order doesn't matter)", new Mocha(new Whip(new Espresso())));
        show("Espresso + Mocha only", new Mocha(new Espresso()));

        System.out.println("\n=== Where checking the description goes wrong ===");
        show("Mochaccino + Whip (no Mocha add-on!)", new Whip(new Mochaccino()));
        System.out.println("  -> the description check gives a discount it shouldn't; the type check doesn't");
    }
}
