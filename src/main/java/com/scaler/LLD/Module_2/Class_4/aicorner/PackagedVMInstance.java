package com.scaler.LLD.Module_2.Class_4.aicorner;

import com.scaler.LLD.Module_2.Class_4.Prototype;

import java.util.ArrayList;
import java.util.List;

/**
 * AI Corner activity: VMInstance with a List<String> installedPackages field.
 * The copy constructor must give the copy its OWN list; copying the reference
 * (this.installedPackages = o.installedPackages) makes every clone share one list.
 */
public class PackagedVMInstance implements Prototype<PackagedVMInstance> {
    private final String os;
    private String hostname;
    private final List<String> installedPackages;

    public PackagedVMInstance(String os, String hostname, List<String> installedPackages) {
        this.os = os;
        this.hostname = hostname;
        this.installedPackages = new ArrayList<>(installedPackages);
    }

    protected PackagedVMInstance(PackagedVMInstance o) {
        this.os = o.os;
        this.hostname = o.hostname;
        // New list with the same elements. Enough here because String is immutable;
        // a list of mutable objects would also need each element copied.
        this.installedPackages = new ArrayList<>(o.installedPackages);
    }

    @Override
    public PackagedVMInstance clone() {
        return new PackagedVMInstance(this);
    }

    public void install(String pkg) {
        installedPackages.add(pkg);
    }

    /** Read-only view, so callers can't modify the list behind the object's back. */
    public List<String> getInstalledPackages() {
        return List.copyOf(installedPackages);
    }

    public String getOs() {
        return os;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    @Override
    public String toString() {
        return "PackagedVMInstance{os='" + os + "', hostname=" + hostname + ", installedPackages=" + installedPackages + "}";
    }
}
