package com.scaler.LLD.Module_2.Class_7.Decorator;

import java.util.List;

/**
 * Decorator, step by step:
 *  1. One coffee class with a list of add-ons and a discount if-else (breaks SRP + OCP)
 *  2. A class per combination (class explosion)
 *  3. A condiment that is NOT a Coffee can't be wrapped again
 *  4. Decorators: construction inside -> out, calls outside -> in, results back out
 *  5. Each wrap creates a NEW object (it's the same TYPE, not the same object)
 */
public class DecoratorEvolutionDemo {

    /** Step 1: everything in one class. Every new rule means editing cost(). */
    static class AmericanoWithAddOns {
        private final List<String> addOns;

        AmericanoWithAddOns(List<String> addOns) {
            this.addOns = addOns;
        }

        int cost() {
            int price = 150;
            for (String addOn : addOns) {
                if (addOn.equals("Mocha")) {
                    price += 50;
                } else if (addOn.equals("Whip")) {
                    price += 60;
                }
            }
            long mochaCount = addOns.stream().filter("Mocha"::equals).count();
            if (mochaCount >= 2) {
                price = price * 90 / 100;   // 10% off for two mochas -- a pricing rule living inside a coffee
            }
            return price;
        }
    }

    /** Step 3: a condiment that is not a Coffee. */
    static class StandaloneMocha {
        private final Coffee coffee;

        StandaloneMocha(Coffee coffee) {
            this.coffee = coffee;
        }

        int cost() {
            return coffee.cost() + 50;
        }
    }

    /** Step 4: a decorator that prints when calls go in and results come back out. */
    static class Traced implements Coffee {
        private final String name;
        private final int price;
        private final Coffee inner;
        private final int depth;

        Traced(String name, int price, Coffee inner, int depth) {
            this.name = name;
            this.price = price;
            this.inner = inner;
            this.depth = depth;
            System.out.println("  constructed " + name);
        }

        @Override
        public int cost() {
            String pad = "  ".repeat(depth);
            System.out.println("  " + pad + "-> " + name + ".cost() called");
            int total = (inner == null ? 0 : inner.cost()) + price;
            System.out.println("  " + pad + "<- " + name + " returns " + total);
            return total;
        }

        @Override
        public String description() {
            return name;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Step 1: one class, list of add-ons, discount if-else ===");
        System.out.println("  Americano + Mocha + Mocha -> Rs." + new AmericanoWithAddOns(List.of("Mocha", "Mocha")).cost()
                + "  (pricing, add-ons and discounts all inside one class)");

        System.out.println("\n=== Step 2: one class per combination ===");
        for (int n = 2; n <= 10; n += 4) {
            System.out.println("  " + n + " optional add-ons (each on/off) -> " + (1 << n) + " classes per base coffee");
        }

        System.out.println("\n=== Step 3: condiment that isn't a Coffee ===");
        StandaloneMocha mocha = new StandaloneMocha(new Espresso());
        System.out.println("  espresso + mocha = Rs." + mocha.cost());
        System.out.println("  new WhippedCream(mocha) doesn't compile: StandaloneMocha is not a Coffee");

        System.out.println("\n=== Step 4: build order vs call order ===");
        Coffee espresso = new Traced("Espresso", 100, null, 3);
        Coffee innerMocha = new Traced("Mocha", 50, espresso, 2);
        Coffee whip = new Traced("Whip", 60, innerMocha, 1);
        Coffee outerMocha = new Traced("Mocha(outer)", 50, whip, 0);
        System.out.println("  total = " + outerMocha.cost());

        System.out.println("\n=== Step 5: each wrap is a NEW object of the same type ===");
        Coffee coffee = new Espresso();
        Coffee before = coffee;
        coffee = new Mocha(coffee);
        System.out.println("  same object after coffee = new Mocha(coffee)? " + (before == coffee)
                + "  -- still a Coffee? " + (coffee instanceof Coffee));
    }
}
