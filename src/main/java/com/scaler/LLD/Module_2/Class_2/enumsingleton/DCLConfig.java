package com.scaler.LLD.Module_2.Class_2.enumsingleton;

import java.io.Serializable;

/** Correct Double-Checked Locking singleton -- still broken by serialization and reflection. */
public class DCLConfig implements Serializable {
    private static final long serialVersionUID = 1L;
    private static volatile DCLConfig instance;

    private final String appName = "LLD-App";

    private DCLConfig() {
    }

    public static DCLConfig getInstance() {
        if (instance == null) {
            synchronized (DCLConfig.class) {
                if (instance == null) {
                    instance = new DCLConfig();
                }
            }
        }
        return instance;
    }

    public String getAppName() {
        return appName;
    }
}
