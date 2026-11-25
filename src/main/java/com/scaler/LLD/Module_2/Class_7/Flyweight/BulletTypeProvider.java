package com.scaler.LLD.Module_2.Class_7.Flyweight;

import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Flyweight factory (a registry of shared BulletTypes): hands out the SAME BulletType object
 * for a given name every time. Names are case-insensitive; an unknown name fails immediately
 * instead of silently creating bullets with a null type.
 */
public class BulletTypeProvider {
    private static final Map<String, BulletType> bulletTypeRegistry = new ConcurrentHashMap<>();

    static {
        registerBulletType("AK47", new BulletType(
                "Brown",
                100,
                7,
                20,
                10
        ));
    }

    private BulletTypeProvider() {
    }

    public static void registerBulletType(String bulletTypeName, BulletType bulletType) {
        bulletTypeRegistry.put(key(bulletTypeName), bulletType);
    }

    public static BulletType getBulletType(String bulletType) {
        BulletType type = bulletTypeRegistry.get(key(bulletType));
        if (type == null) {
            throw new IllegalArgumentException("Unknown bullet type: " + bulletType + ". Registered: " + names());
        }
        return type;
    }

    public static Set<String> names() {
        return new TreeSet<>(bulletTypeRegistry.keySet());
    }

    private static String key(String name) {
        return name.toLowerCase(Locale.ROOT);
    }
}
