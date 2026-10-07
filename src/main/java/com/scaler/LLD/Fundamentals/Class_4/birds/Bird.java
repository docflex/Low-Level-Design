package com.scaler.LLD.Fundamentals.Class_4.birds;

/**
 * LLD 4 — OOP-3: Bird — Abstract Base Class
 *
 * Holds common bird attributes. Does NOT contain fly() —
 * that's an interface (Flyable) because not all birds fly.
 *
 * WITHOUT interfaces (class explosion):
 *   Bird
 *     ├── FlyableBird
 *     │     ├── FlyableDancingBird
 *     │     └── FlyableNonDancingBird
 *     └── NonFlyableBird
 *           ├── NonFlyableDancingBird
 *           └── NonFlyableNonDancingBird
 *
 *   2 behaviors → 4 classes.  N behaviors → 2^N classes!
 *
 * WITH interfaces (clean):
 *   Bird (abstract)
 *     ├── Dove    implements Flyable
 *     ├── Pigeon  implements Flyable, Danceable
 *     └── Penguin implements Danceable
 */
public abstract class Bird {

    protected String species;

    public Bird(String species) {
        this.species = species;
    }

    @Override
    public String toString() {
        return species;
    }
}
