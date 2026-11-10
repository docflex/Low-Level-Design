package com.scaler.LLD.Module_2.Class_3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Every step from the class, runnable side by side:
 *  1. Big constructor        2. Telescoping constructors   3. Map<String, Object>
 *  4. Helper class            5. Separate Builder class      6. Final Student builder
 *  + the AI-review issue: a builder that stores a caller's list by reference.
 */
public class BuilderEvolutionDemo {

    static void validateGraduationYear(int graduationYear) {
        if (graduationYear < 2022) {
            throw new IllegalArgumentException("Graduation Year Cannot Be less than 2022");
        }
    }

    /** Step 1: one constructor with every field. */
    static class BigConstructorStudent {
        final String name;
        final String phoneNumber;
        final String universityName;

        BigConstructorStudent(long id, int age, double psp, String name, String batch,
                              int graduationYear, String phoneNumber, String universityName) {
            validateGraduationYear(graduationYear);
            this.name = name;
            this.phoneNumber = phoneNumber;
            this.universityName = universityName;
        }
    }

    /** Step 2: telescoping constructors -- each one fills a default and calls the next. */
    static class Pizza {
        final int size;
        final boolean cheese;
        final boolean pepperoni;

        Pizza(int size) { this(size, true); }
        Pizza(int size, boolean cheese) { this(size, cheese, false); }
        Pizza(int size, boolean cheese, boolean pepperoni) {
            this.size = size;
            this.cheese = cheese;
            this.pepperoni = pepperoni;
        }
    }

    /** Step 3: a Map as input -- readable, but no type checking and no key checking. */
    static class MapStudent {
        long id;
        String name;
        int graduationYear;

        MapStudent(Map<String, Object> map) {
            if (map.containsKey("graduationYear")) validateGraduationYear((int) map.get("graduationYear"));
            if (map.containsKey("id")) this.id = (long) map.get("id");
            if (map.containsKey("name")) this.name = (String) map.get("name");
            if (map.containsKey("graduationYear")) this.graduationYear = (int) map.get("graduationYear");
        }
    }

    /** Step 4: a helper class -- a type-safe "map" with named, typed fields. */
    static class Helper {
        long id;
        String name;
        int graduationYear;
    }

    static class HelperStudent {
        final long id;
        final String name;

        HelperStudent(Helper helper) {
            validateGraduationYear(helper.graduationYear);
            this.id = helper.id;
            this.name = helper.name;
        }
    }

    /** Step 5: the separate Builder class (Builder.java) with void setters. */
    static class BuilderStudent {
        final long id;
        final String name;

        BuilderStudent(Builder builder) {
            validateGraduationYear(builder.getGraduationYear());
            this.id = builder.getId();
            this.name = builder.getName();
        }
    }

    /** AI review: the kind of builder an AI often generates -- stores the caller's list as-is. */
    static final class AiStudent {
        private final List<String> phoneNumbers;

        private AiStudent(AiBuilder builder) {
            this.phoneNumbers = builder.copyList ? List.copyOf(builder.phoneNumbers) : builder.phoneNumbers;
        }

        List<String> getPhoneNumbers() { return phoneNumbers; }

        static class AiBuilder {
            private List<String> phoneNumbers = new ArrayList<>();
            private boolean copyList;

            AiBuilder phoneNumbers(List<String> phoneNumbers) { this.phoneNumbers = phoneNumbers; return this; }
            AiBuilder defensiveCopy(boolean copyList) { this.copyList = copyList; return this; }
            AiStudent build() { return new AiStudent(this); }
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Step 1: big constructor ===");
        BigConstructorStudent ok = new BigConstructorStudent(1234, 23, 93, "Rehber", "DS/ML", 2024, "1234567890", "ABC");
        System.out.println("  phone=" + ok.phoneNumber + ", university=" + ok.universityName);
        // Both are Strings, so swapping them compiles and runs -- a silent bug
        BigConstructorStudent swapped = new BigConstructorStudent(1234, 23, 93, "Rehber", "DS/ML", 2024, "ABC", "1234567890");
        System.out.println("  swapped args -> phone=" + swapped.phoneNumber + ", university=" + swapped.universityName + "  (no error!)");
        try {
            new BigConstructorStudent(1234, 23, 93, "Rehber", "DS/ML", 2020, "1234567890", "ABC");
        } catch (IllegalArgumentException e) {
            System.out.println("  the script's client (graduationYear 2020) -> " + e.getMessage());
        }

        System.out.println("\n=== Step 2: telescoping constructors ===");
        Pizza small = new Pizza(8);
        Pizza noCheese = new Pizza(10, false);
        System.out.println("  new Pizza(8)        -> size=" + small.size + ", cheese=" + small.cheese + ", pepperoni=" + small.pepperoni);
        System.out.println("  new Pizza(10, false)-> size=" + noCheese.size + ", cheese=" + noCheese.cheese + ", pepperoni=" + noCheese.pepperoni);
        System.out.println("  pepperoni without choosing cheese? not possible -- optional params must come in order");

        System.out.println("\n=== Step 3: Map<String, Object> ===");
        Map<String, Object> config = new HashMap<>();
        config.put("id", 1234L);
        config.put("nmae", "Rehber");                 // typo in the key
        config.put("graduationYear", 2024);
        System.out.println("  misspelled key 'nmae' -> name = " + new MapStudent(config).name + "  (no error)");
        config.put("id", 1234);                       // an int, as in the script
        try {
            new MapStudent(config);
        } catch (ClassCastException e) {
            System.out.println("  put(\"id\", 1234) -> ClassCastException: Integer cannot be cast to Long");
        }

        System.out.println("\n=== Step 4: helper class ===");
        Helper helper = new Helper();
        helper.id = 1234;
        helper.name = "Rehber";
        helper.graduationYear = 2024;
        HelperStudent s4 = new HelperStudent(helper);
        System.out.println("  HelperStudent id=" + s4.id + ", name=" + s4.name);

        System.out.println("\n=== Step 5: separate Builder class, void setters (N fields = N + 1 statements) ===");
        Builder builder = new Builder();
        builder.setId(1234);
        builder.setName("Rehber");
        builder.setGraduationYear(2024);
        BuilderStudent s5 = new BuilderStudent(builder);
        System.out.println("  BuilderStudent id=" + s5.id + ", name=" + s5.name);

        System.out.println("\n=== Step 6: final builder -- one chained statement ===");
        Student s6 = Student.getBuilder().setId(1234).setName("Rehber").setGraduationYear(2024).build();
        System.out.println("  " + s6);

        System.out.println("\n=== AI review: list stored by reference ===");
        List<String> phones = new ArrayList<>(List.of("111"));
        AiStudent aliased = new AiStudent.AiBuilder().phoneNumbers(phones).build();
        AiStudent copied = new AiStudent.AiBuilder().phoneNumbers(phones).defensiveCopy(true).build();
        phones.add("999");
        System.out.println("  caller adds 999 after build()");
        System.out.println("  no copy        -> " + aliased.getPhoneNumbers() + "  (changed, even though the field is final)");
        System.out.println("  List.copyOf()  -> " + copied.getPhoneNumbers());
    }
}
