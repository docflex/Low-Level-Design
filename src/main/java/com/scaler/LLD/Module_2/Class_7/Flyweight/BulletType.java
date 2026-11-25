package com.scaler.LLD.Module_2.Class_7.Flyweight;

/**
 * Flyweight: the intrinsic (shared) state of a bullet. One BulletType object is shared by
 * thousands of bullets, so it MUST be immutable -- otherwise changing one bullet's damage
 * would change it for every bullet of that type.
 */
public final class BulletType {
    // Keep all the intrinsic Properties Here.
    private final String color;
    private final int speed;
    private final double radius;
    private final int maxRange;
    private final int damage;

    public BulletType(String color, int speed, double radius, int maxRange, int damage) {
        this.color = color;
        this.speed = speed;
        this.radius = radius;
        this.maxRange = maxRange;
        this.damage = damage;
    }

    public String getColor() {
        return color;
    }

    public int getSpeed() {
        return speed;
    }

    public double getRadius() {
        return radius;
    }

    public int getMaxRange() {
        return maxRange;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public String toString() {
        return "BulletType{color=" + color + ", speed=" + speed + ", radius=" + radius
                + ", maxRange=" + maxRange + ", damage=" + damage + "}";
    }
}
