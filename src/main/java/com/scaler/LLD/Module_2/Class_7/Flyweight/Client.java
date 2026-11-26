package com.scaler.LLD.Module_2.Class_7.Flyweight;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

/** Flyweight: 50,000 bullets of two types share just two BulletType objects. */
public class Client {
    public static void main(String[] args) {
        BulletTypeProvider.registerBulletType("M416", new BulletType("Gold", 120, 5.56, 25, 8));
        System.out.println("=== Registered bullet types: " + BulletTypeProvider.names() + " ===");

        Random random = new Random(42);
        List<Bullet> bulletList = new ArrayList<>();
        for (int i = 0; i < 50_000; i++) {
            bulletList.add(new Bullet(
                    i % 2 == 0 ? "AK47" : "M416",
                    random.nextInt(100),
                    random.nextInt(100)
            ));
        }

        Set<BulletType> distinctTypes = Collections.newSetFromMap(new IdentityHashMap<>());
        for (Bullet bullet : bulletList) {
            distinctTypes.add(bullet.getBulletType());
        }
        System.out.println("  bullets created: " + bulletList.size());
        System.out.println("  distinct BulletType objects in memory: " + distinctTypes.size());

        System.out.println("\n=== Extrinsic state is per bullet ===");
        Bullet first = bulletList.get(0);
        Bullet third = bulletList.get(2);
        first.move(5, 5);
        System.out.println("  bullet 0 at (" + first.getCoordX() + "," + first.getCoordY() + "), bullet 2 at ("
                + third.getCoordX() + "," + third.getCoordY() + ")");
        System.out.println("  same AK47 BulletType object? " + (first.getBulletType() == third.getBulletType()));

        System.out.println("\n=== Lookup is case-insensitive; unknown types fail immediately ===");
        System.out.println("  \"ak47\" -> " + BulletTypeProvider.getBulletType("ak47"));
        try {
            new Bullet("AWM", 0, 0);
        } catch (IllegalArgumentException e) {
            System.out.println("  " + e.getMessage());
        }
    }
}
