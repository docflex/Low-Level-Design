package com.scaler.LLD.Module_2.Class_7.aicorner;

/** The concrete drinks and add-ons for the AI Corner example. */
public final class Drinks {
    private Drinks() {
    }

    public static class Espresso implements Beverage {
        @Override
        public int cost() {
            return 100;
        }

        @Override
        public String description() {
            return "Espresso";
        }
    }

    /** A base drink whose NAME happens to contain "Mocha" -- trips up description-based checks. */
    public static class Mochaccino implements Beverage {
        @Override
        public int cost() {
            return 140;
        }

        @Override
        public String description() {
            return "Mochaccino";
        }
    }

    public static class Mocha extends AddOn {
        public Mocha(Beverage beverage) {
            super(beverage);
        }

        @Override
        public int cost() {
            return beverage.cost() + 50;
        }

        @Override
        public String description() {
            return beverage.description() + " with Mocha";
        }
    }

    public static class Whip extends AddOn {
        public Whip(Beverage beverage) {
            super(beverage);
        }

        @Override
        public int cost() {
            return beverage.cost() + 60;
        }

        @Override
        public String description() {
            return beverage.description() + " with Whip";
        }
    }

    /**
     * Combo discount: 10% off the WHOLE order if it has both Mocha and Whip.
     * Wrap it outermost, so it sees the final total. It checks the decorator chain
     * by type -- not by searching the description text.
     */
    public static class ComboDiscount extends AddOn {
        public ComboDiscount(Beverage beverage) {
            super(beverage);
        }

        private boolean applies() {
            return beverage.contains(Mocha.class) && beverage.contains(Whip.class);
        }

        @Override
        public int cost() {
            int total = beverage.cost();
            return applies() ? total - total / 10 : total;
        }

        @Override
        public String description() {
            return beverage.description() + (applies() ? " (combo: 10% off)" : "");
        }
    }
}
