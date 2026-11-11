package com.scaler.LLD.Module_2.Class_4;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Registry: one central place that stores templates by name and hands out copies.
 *
 * - Static map: one shared registry for the whole application (no instances needed)
 * - ConcurrentHashMap: templates can be added at runtime (e.g. from an API) while requests read
 * - Clone on the way IN: the caller can't change a template through the object it registered
 * - Clone on the way OUT: the caller can't change a template through the copy it received
 * - Unknown name: fails fast with a clear message instead of a NullPointerException
 */
public class VMInstanceRegistry {

    private static final Map<String, VMInstance> registry = new ConcurrentHashMap<>();

    private VMInstanceRegistry() {
    }

    public static void addVMInstance(String name, VMInstance instance) {
        registry.put(name, instance.clone());
    }

    public static VMInstance getInstance(String name) {
        VMInstance template = registry.get(name);
        if (template == null) {
            throw new IllegalArgumentException("No template registered with name '" + name + "'. Available: " + names());
        }
        return template.clone();
    }

    public static Set<String> names() {
        return new TreeSet<>(registry.keySet());
    }
}
