package com.scaler.LLD.Module_2.Class_4.aicorner;

import java.util.ArrayList;
import java.util.List;

/**
 * AI Corner: "Add a List<String> installedPackages field to VMInstance and update clone()."
 * Shows the shallow-copy answer many models give, and the correct one.
 */
public class ShallowCopyDemo {

    /** What a model often generates: the copy constructor copies the list reference. */
    static class ShallowVM {
        final String os;
        final List<String> installedPackages;

        ShallowVM(String os, List<String> installedPackages) {
            this.os = os;
            this.installedPackages = installedPackages;
        }

        ShallowVM(ShallowVM o) {
            this.os = o.os;
            this.installedPackages = o.installedPackages;   // same list object
        }

        ShallowVM cloneVm() {
            return new ShallowVM(this);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Shallow copy (reference copied) ===");
        ShallowVM shallowTemplate = new ShallowVM("Ubuntu 22.04", new ArrayList<>(List.of("nginx", "docker")));
        ShallowVM shallowClone = shallowTemplate.cloneVm();
        shallowClone.installedPackages.add("redis");
        System.out.println("  clone installs redis");
        System.out.println("  template packages -> " + shallowTemplate.installedPackages + "  <-- template changed");
        System.out.println("  same list object? " + (shallowTemplate.installedPackages == shallowClone.installedPackages));

        System.out.println("\n=== Correct copy (new list per clone) ===");
        List<String> base = new ArrayList<>(List.of("nginx", "docker"));
        PackagedVMInstance template = new PackagedVMInstance("Ubuntu 22.04", null, base);
        PackagedVMInstance clone = template.clone();
        clone.install("redis");
        base.add("mysql");   // the caller's original list doesn't leak in either
        System.out.println("  clone installs redis, caller adds mysql to its own list");
        System.out.println("  template -> " + template.getInstalledPackages());
        System.out.println("  clone    -> " + clone.getInstalledPackages());
    }
}
