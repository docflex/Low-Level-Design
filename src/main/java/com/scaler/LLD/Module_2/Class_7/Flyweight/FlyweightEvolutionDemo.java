package com.scaler.LLD.Module_2.Class_7.Flyweight;

/**
 * Flyweight, step by step:
 *  1. Measured memory: a bullet with every field vs a flyweight bullet
 *  2. Shared state must be immutable
 *  3. Why static fields don't work for multiple bullet types
 *  4. Why cloning a prototype per bullet would defeat the purpose
 *
 * Memory numbers are measured with Runtime's used-heap counter, so they're approximate; on a
 * 64-bit JVM with compressed object pointers (the default for heaps under ~32 GB) expect
 * about 48 vs 24 bytes per object.
 */
public class FlyweightEvolutionDemo {

    /** Every field stored in every bullet. */
    static class FatBullet {
        final int size;
        final String color;
        final int speed;
        int xCoord;
        int yCoord;
        final double radius;
        final int maxRange;
        final int damage;

        FatBullet(String color, int x, int y) {
            this.size = 7;
            this.color = color;
            this.speed = 100;
            this.xCoord = x;
            this.yCoord = y;
            this.radius = 7;
            this.maxRange = 20;
            this.damage = 10;
        }
    }

    /** A flyweight bullet: a shared reference plus two ints. */
    static class LightBullet {
        final BulletType type;
        int xCoord;
        int yCoord;

        LightBullet(BulletType type, int x, int y) {
            this.type = type;
            this.xCoord = x;
            this.yCoord = y;
        }
    }

    /** Step 2: what goes wrong if the shared object can be modified. */
    static class MutableBulletType {
        int damage = 10;
    }

    static class MutableTypeBullet {
        final MutableBulletType type;

        MutableTypeBullet(MutableBulletType type) {
            this.type = type;
        }
    }

    /** Step 3: intrinsic fields as static -- one value for ALL bullets of every type. */
    static class StaticFieldBullet {
        static int damage;
        int xCoord;
    }

    static long usedHeap() {
        Runtime runtime = Runtime.getRuntime();
        for (int i = 0; i < 3; i++) {
            System.gc();
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return runtime.totalMemory() - runtime.freeMemory();
    }

    /** Bytes per object for n objects made by the factory; the holder array is allocated before measuring. */
    static double measureBytesPerObject(int n, java.util.function.IntFunction<Object> factory) {
        Object[] holder = new Object[n];
        long before = usedHeap();
        for (int i = 0; i < n; i++) {
            holder[i] = factory.apply(i);
        }
        long after = usedHeap();
        if (holder[n - 1] == null) {
            throw new IllegalStateException("objects were not kept alive");
        }
        return (after - before) / (double) n;
    }

    public static void main(String[] args) {
        int n = 1_000_000;
        BulletType ak47 = new BulletType("Brown", 100, 7, 20, 10);

        System.out.println("=== Step 1: measured memory per bullet object (" + n + " bullets) ===");
        usedHeap();   // warm up: settle the heap before the first measurement
        double fatBytes = measureBytesPerObject(n, i -> new FatBullet("Brown", i, i));
        double lightBytes = measureBytesPerObject(n, i -> new LightBullet(ak47, i, i));
        if (fatBytes < 8 || lightBytes < 8) {
            // GC noise swamped the measurement; fall back to the layout on a 64-bit JVM with compressed pointers
            System.out.println("  (measurement was noisy on this run -- showing the expected sizes instead)");
            fatBytes = 48;
            lightBytes = 24;
        }
        System.out.printf("  bullet with every field : ~%.0f bytes%n", fatBytes);
        System.out.printf("  flyweight bullet        : ~%.0f bytes (+ one shared BulletType)%n", lightBytes);
        System.out.printf("  saving                  : ~%.1fx -- object headers and references count too%n",
                fatBytes / lightBytes);

        System.out.println("\n=== Step 2: shared state must be immutable ===");
        MutableBulletType sharedType = new MutableBulletType();
        MutableTypeBullet a = new MutableTypeBullet(sharedType);
        MutableTypeBullet b = new MutableTypeBullet(sharedType);
        a.type.damage = 999;   // meant to buff ONE bullet
        System.out.println("  buffed bullet a; bullet b's damage is now " + b.type.damage + "  <-- every bullet changed");
        System.out.println("  BulletType has only final fields and no setters, so this can't happen");

        System.out.println("\n=== Step 3: intrinsic state as static fields ===");
        StaticFieldBullet.damage = 10;   // AK47
        StaticFieldBullet.damage = 8;    // now someone sets up M416...
        System.out.println("  AK47 bullets now do " + StaticFieldBullet.damage + " damage  <-- one value for ALL types");

        System.out.println("\n=== Step 4: clone a prototype per bullet? ===");
        int copies = 0;
        BulletType previous = null;
        for (int i = 0; i < 5; i++) {
            BulletType perBulletCopy = new BulletType(ak47.getColor(), ak47.getSpeed(), ak47.getRadius(),
                    ak47.getMaxRange(), ak47.getDamage());
            if (perBulletCopy != previous) {
                copies++;
            }
            previous = perBulletCopy;
        }
        System.out.println("  5 bullets -> " + copies + " BulletType copies: memory grows again -- share, don't copy");
    }
}
