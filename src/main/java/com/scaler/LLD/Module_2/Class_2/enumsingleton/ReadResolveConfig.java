package com.scaler.LLD.Module_2.Class_2.enumsingleton;

import java.io.Serializable;

/**
 * Manual fix for serialization: readResolve() swaps the freshly deserialized copy
 * for the real instance. Reflection can still call the private constructor.
 */
public class ReadResolveConfig implements Serializable {
    private static final long serialVersionUID = 1L;
    private static volatile ReadResolveConfig instance;

    private ReadResolveConfig() {
    }

    public static ReadResolveConfig getInstance() {
        if (instance == null) {
            synchronized (ReadResolveConfig.class) {
                if (instance == null) {
                    instance = new ReadResolveConfig();
                }
            }
        }
        return instance;
    }

    /** Called by ObjectInputStream after deserializing; its return value replaces the new copy. */
    private Object readResolve() {
        return getInstance();
    }
}
