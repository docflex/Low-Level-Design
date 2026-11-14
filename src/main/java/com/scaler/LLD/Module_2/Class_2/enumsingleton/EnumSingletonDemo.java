package com.scaler.LLD.Module_2.Class_2.enumsingleton;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;

/**
 * Homework: How do enums fix the two ways a singleton breaks?
 *  1. Serialization / deserialization  2. Reflection on the private constructor
 */
public class EnumSingletonDemo {

    /** Object -> bytes -> object, the same thing that happens when a config is saved or sent over the network. */
    static Object serializeAndDeserialize(Object original) throws Exception {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(bytes)) {
            out.writeObject(original);
        }
        try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes.toByteArray()))) {
            return in.readObject();
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("=== 1. Serialization ===");
        DCLConfig dcl = DCLConfig.getInstance();
        DCLConfig dclCopy = (DCLConfig) serializeAndDeserialize(dcl);
        System.out.println("  DCLConfig         same object? " + (dcl == dclCopy)
                + "  (content equal: " + dcl.getAppName().equals(dclCopy.getAppName()) + ") -> BROKEN");

        ReadResolveConfig rr = ReadResolveConfig.getInstance();
        System.out.println("  ReadResolveConfig same object? " + (rr == serializeAndDeserialize(rr)) + "  -> fixed by readResolve()");

        EnumConfig e = EnumConfig.INSTANCE;
        System.out.println("  EnumConfig        same object? " + (e == serializeAndDeserialize(e)) + "  -> safe by design");

        System.out.println("\n=== 2. Reflection ===");
        Constructor<DCLConfig> dclCtor = DCLConfig.class.getDeclaredConstructor();
        dclCtor.setAccessible(true);
        System.out.println("  DCLConfig         same object? " + (dclCtor.newInstance() == dcl) + " -> BROKEN");

        Constructor<ReadResolveConfig> rrCtor = ReadResolveConfig.class.getDeclaredConstructor();
        rrCtor.setAccessible(true);
        System.out.println("  ReadResolveConfig same object? " + (rrCtor.newInstance() == rr) + " -> still BROKEN");

        // An enum's only constructor is the compiler-generated (String name, int ordinal)
        Constructor<EnumConfig> enumCtor = EnumConfig.class.getDeclaredConstructor(String.class, int.class);
        enumCtor.setAccessible(true);
        try {
            enumCtor.newInstance("SECOND", 1);
            System.out.println("  EnumConfig        second instance created -> BROKEN");
        } catch (IllegalArgumentException ex) {
            System.out.println("  EnumConfig        blocked: " + ex.getMessage());
        }

        System.out.println("\n=== Summary ===");
        System.out.println("  Threat          DCL      readResolve   enum");
        System.out.println("  Threads         safe     safe          safe");
        System.out.println("  Serialization   broken   safe          safe");
        System.out.println("  Reflection      broken   broken        safe");
    }
}
