package com.scaler.LLD.Module_2.Class_7.Flyweight;

/**
 * One bullet in flight: a reference to its shared BulletType (intrinsic) plus its own
 * position (extrinsic). With compressed object pointers on a 64-bit JVM this object is
 * about 24 bytes, versus about 48 for a bullet that stores every field itself.
 */
public class Bullet {
    private final BulletType bulletType;

    // Keep Extrinsic Stuff Here
    private int coordX;
    private int coordY;

    public Bullet(String bulletType, int coordX, int coordY) {
        this.bulletType = BulletTypeProvider.getBulletType(bulletType);
        this.coordX = coordX;
        this.coordY = coordY;
    }

    /** Extrinsic state changes per bullet; the shared BulletType never does. */
    public void move(int dx, int dy) {
        coordX += dx;
        coordY += dy;
    }

    public BulletType getBulletType() {
        return bulletType;
    }

    public int getCoordX() {
        return coordX;
    }

    public int getCoordY() {
        return coordY;
    }
}
