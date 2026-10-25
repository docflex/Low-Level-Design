package com.scaler.LLD.Module_1.Part_3_Advanced_Java.Class_1;

import java.util.ArrayList;
import java.util.List;

/**
 * Java Advanced Concepts - 1 [Generics]
 *
 * Demonstrates generics with inheritance:
 * - Dog extends Animal, but List&lt;Dog&gt; is NOT a subtype of List&lt;Animal&gt;
 * - Upper bounded wildcard: ? extends Animal (producer -- read items out)
 * - Lower bounded wildcard: ? super Animal  (consumer -- put items in)
 * - PECS: Producer Extends, Consumer Super
 */
public class InheritanceDemo {

    static void doSomething(Animal animal) {
        System.out.println("  " + animal.getName());
    }

    /**
     * Upper bounded wildcard (? extends Animal):
     * Accepts List&lt;Animal&gt;, List&lt;Dog&gt;, or any Animal subtype list.
     * We READ from the list (producer) -- hence "extends".
     */
    static void printNames(List<? extends Animal> animalList) {
        for (Animal animal : animalList) {
            System.out.println("  " + animal.getName());
        }
    }

    /**
     * Lower bounded wildcard (? super Animal):
     * Accepts List&lt;Animal&gt;, List&lt;Creature&gt;, or any Animal supertype list.
     * We WRITE into the list (consumer) -- hence "super".
     */
    static void addAnimal(List<? super Animal> animalList) {
        animalList.add(new Animal("Unknown Animal"));
    }

    public static void main(String[] args) {
        // ── Part 1: Normal polymorphism works ──
        System.out.println("=== Normal polymorphism ===");
        Animal animal = new Animal("Tiger");
        doSomething(animal);

        Animal animal2 = new Dog("Labrador");
        doSomething(animal2);

        // ── Part 2: List<Animal> works with printNames ──
        System.out.println("\n=== printNames(List<Animal>) ===");
        List<Animal> animals = List.of(animal, animal2);
        printNames(animals);

        // ── Part 3: List<Dog> also works with ? extends Animal ──
        // Without the wildcard, this would NOT compile!
        System.out.println("\n=== printNames(List<Dog>) -- needs ? extends Animal ===");
        List<Dog> dogs = new ArrayList<>();
        dogs.add(new Dog("German Shepherd"));
        dogs.add(new Dog("Husky"));
        printNames(dogs);

        // ── Part 4: Lower bounded wildcard -- ? super Animal ──
        System.out.println("\n=== addAnimal(List<Creature>) -- needs ? super Animal ===");
        List<Creature> creatureList = new ArrayList<>();
        addAnimal(creatureList);
        System.out.println("  Creature list size after addAnimal: " + creatureList.size());
    }
}
