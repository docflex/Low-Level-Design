package com.scaler.LLD.Module_2.Class_2.enumsingleton;

/**
 * Enum singleton: thread-safe, serialization-safe and reflection-safe with no extra code.
 *
 * Under the hood (verified with javap and in EnumSingletonDemo):
 *  - INSTANCE compiles to "public static final EnumConfig INSTANCE", created in the enum's
 *    static initializer -> the JVM's class-initialization lock makes creation thread-safe
 *  - Serialization writes only the constant's NAME; deserialization calls Enum.valueOf(...)
 *    and returns the existing constant (readObject / readResolve are ignored for enums)
 *  - Constructor.newInstance() refuses enum types: "Cannot reflectively create enum objects"
 *  - Enum.clone() is final and throws CloneNotSupportedException
 *
 * Trade-offs: always eager (created on first use of the enum), cannot extend another class
 * (it already extends java.lang.Enum), but it can implement interfaces.
 */
public enum EnumConfig {
    INSTANCE;

    private final String appName = "LLD-App";

    public String getAppName() {
        return appName;
    }
}
