package com.scaler.LLD.Fundamentals.Class_3.animals;

/**
 * LLD 3 — OOP-2: Elephant — Access Modifier Demo
 *
 * This class uses different access modifiers on its fields:
 *   - legs:   protected  → accessible by subclasses (even in different packages)
 *   - height: default    → accessible only within the SAME package
 *   - name:   public     → accessible from everywhere
 *   - id:     private    → accessible only within this class
 *
 * See BabyElephant (in a DIFFERENT package) to see what it can/can't access.
 */
public class Elephant {

    private int id = 1;                 // private:    this class only
    int height = 300;                   // default:    this class + same package
    protected int legs = 4;             // protected:  this class + same package + subclasses
    public String name = "Elephant";    // public:     everywhere

    public int getId() { return id; }   // public getter for private field

    @Override
    public String toString() {
        return "Elephant{id=" + id + ", name='" + name
                + "', legs=" + legs + ", height=" + height + "}";
    }
}
