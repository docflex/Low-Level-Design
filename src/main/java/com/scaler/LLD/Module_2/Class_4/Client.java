package com.scaler.LLD.Module_2.Class_4;

/** Prototype + Registry together: register templates once, then clone them on demand. */
public class Client {

    static void prefillRegistry() {
        VMInstance ubuntu =
                new VMInstance("Ubuntu 22.04", "Docker 1.4", "Datadog", null, null);
        VMInstance redHat =
                new VMInstance("RedHat", "Docker 1.2", "Splunk", null, null);
        GPU_VMInstance nvidiaH100 =
                new GPU_VMInstance(ubuntu, "Nvidia H100");
        GPU_VMInstance nvidiaGB300 =
                new GPU_VMInstance(ubuntu, "Nvidia GB300");
        GPU_VMInstance amd9070 =
                new GPU_VMInstance(redHat, "AMD 9070");

        VMInstanceRegistry.addVMInstance("ubuntu-basic", ubuntu);
        VMInstanceRegistry.addVMInstance("redHat-legacy", redHat);
        VMInstanceRegistry.addVMInstance("nvidiaH100-High", nvidiaH100);
        VMInstanceRegistry.addVMInstance("nvidiaGB300-XHigh", nvidiaGB300);
        VMInstanceRegistry.addVMInstance("amd9070-Medium", amd9070);
    }

    public static void main(String[] args) {
        prefillRegistry();
        System.out.println("=== Registered templates ===");
        System.out.println("  " + VMInstanceRegistry.names());

        System.out.println("\n=== Clone a basic server and customise it ===");
        VMInstance basicServer = VMInstanceRegistry.getInstance("ubuntu-basic");
        basicServer.setHostname("api-1.scaler.com");
        basicServer.setIpAddress("18.243.93.102");
        System.out.println("  copy     : " + basicServer);
        System.out.println("  template : " + VMInstanceRegistry.getInstance("ubuntu-basic") + "  (unchanged)");

        System.out.println("\n=== Three GPU machines from one template ===");
        for (int i = 1; i <= 3; i++) {
            VMInstance cluster = VMInstanceRegistry.getInstance("nvidiaGB300-XHigh");
            cluster.setHostname("gpu-" + i + ".scaler.com");
            cluster.setIpAddress("10.0.0." + i);
            System.out.println("  " + cluster);
        }

        System.out.println("\n=== Covariant clone(): no cast when you already hold a GPU_VMInstance ===");
        GPU_VMInstance gpu = new GPU_VMInstance("Ubuntu 22.04", "Docker 1.4", "Datadog", "train-1", "10.0.1.1", "Nvidia H100");
        GPU_VMInstance gpuCopy = gpu.clone();
        System.out.println("  " + gpuCopy + "  same object? " + (gpu == gpuCopy));

        System.out.println("\n=== Unknown template name ===");
        try {
            VMInstanceRegistry.getInstance("gpu-training-v1");
        } catch (IllegalArgumentException e) {
            System.out.println("  " + e.getMessage());
        }
    }
}
