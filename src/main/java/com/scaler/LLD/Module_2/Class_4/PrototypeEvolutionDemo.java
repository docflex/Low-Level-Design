package com.scaler.LLD.Module_2.Class_4;

import java.util.HashMap;
import java.util.Map;

/**
 * Every broken step from the class, runnable side by side:
 *  1. Copying field by field          2. The script's instanceof ladder (child silently lost)
 *  3. Quiz 1: child forgets clone()   4. Registry that hands out the template itself
 *  5. Registry that stores the caller's object   6. Quiz 2: missing key
 */
public class PrototypeEvolutionDemo {

    static class VM {
        String os;
        String ipAddress;

        VM(String os, String ipAddress) {
            this.os = os;
            this.ipAddress = ipAddress;
        }

        VM(VM o) {
            this.os = o.os;
            this.ipAddress = o.ipAddress;
        }

        @Override
        public VM clone() {
            return new VM(this);
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "{os=" + os + ", ip=" + ipAddress + "}";
        }
    }

    static class GpuVM extends VM {
        final String gpuType;

        GpuVM(String os, String ipAddress, String gpuType) {
            super(os, ipAddress);
            this.gpuType = gpuType;
        }

        GpuVM(GpuVM o) {
            super(o);
            this.gpuType = o.gpuType;
        }

        @Override
        public GpuVM clone() {
            return new GpuVM(this);
        }

        @Override
        public String toString() {
            return "GpuVM{os=" + os + ", ip=" + ipAddress + ", gpuType=" + gpuType + "}";
        }
    }

    /** Quiz 1: a child that forgets to override clone(). */
    static class ForgetfulGpuVM extends VM {
        final String gpuType;

        ForgetfulGpuVM(String os, String ipAddress, String gpuType) {
            super(os, ipAddress);
            this.gpuType = gpuType;
        }
    }

    /** The script's ladder, in the script's order: parent check first. */
    static VM scriptLadderCopy(VM vm) {
        if (vm instanceof VM) {
            return new VM(vm);
        } else if (vm instanceof GpuVM) {
            return new GpuVM((GpuVM) vm);
        }
        return null;
    }

    /** Same ladder with the child first -- correct, but every new subclass means editing this method. */
    static VM fixedOrderLadderCopy(VM vm) {
        if (vm instanceof GpuVM) {
            return new GpuVM((GpuVM) vm);
        } else if (vm instanceof VM) {
            return new VM(vm);
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println("=== 1. Copy field by field: the client must know every field ===");
        VM vm1 = new VM("Ubuntu 22.04", "18.243.93.102");
        VM vm2 = new VM(null, null);
        vm2.os = vm1.os;
        vm2.ipAddress = vm1.ipAddress;
        System.out.println("  " + vm2 + "  (and a private field without a setter can't be copied at all)");

        System.out.println("\n=== 2. instanceof ladder ===");
        GpuVM gpu = new GpuVM("Ubuntu 22.04", "10.0.0.1", "Nvidia H100");
        System.out.println("  script's order (parent first) -> " + scriptLadderCopy(gpu) + "  <-- gpuType lost");
        System.out.println("  child first                   -> " + fixedOrderLadderCopy(gpu) + "  (works, but breaks OCP)");
        System.out.println("  prototype: gpu.clone()        -> " + gpu.clone() + "  (no ladder at all)");

        System.out.println("\n=== 3. Quiz 1: child forgets to override clone() ===");
        VM lazyCopy = new ForgetfulGpuVM("Ubuntu 22.04", "10.0.0.2", "Nvidia H100").clone();
        System.out.println("  copy is a " + lazyCopy.getClass().getSimpleName() + " -> gpuType silently dropped");
        try {
            ForgetfulGpuVM cast = (ForgetfulGpuVM) lazyCopy;
            System.out.println("  cast worked: " + cast);
        } catch (ClassCastException e) {
            System.out.println("  casting it, as the script's client does -> ClassCastException");
        }

        System.out.println("\n=== 4. Registry returns the template itself (no clone on the way out) ===");
        Map<String, VM> registry = new HashMap<>();
        registry.put("ubuntu-basic", new VM("Ubuntu 22.04", null));
        VM basicServer2 = registry.get("ubuntu-basic");
        basicServer2.ipAddress = "1.1.1.1";
        System.out.println("  template after the client sets its IP -> " + registry.get("ubuntu-basic") + "  <-- template destroyed");

        System.out.println("\n=== 5. Registry stores the caller's object (no clone on the way in) ===");
        Map<String, VM> registry2 = new HashMap<>();
        VM ubuntu = new VM("Ubuntu 22.04", null);
        registry2.put("ubuntu-basic", ubuntu);
        ubuntu.os = "Ubuntu 18.04";
        System.out.println("  caller edits its own variable later -> template = " + registry2.get("ubuntu-basic") + "  <-- changed too");

        System.out.println("\n=== 6. Quiz 2: unknown key ===");
        VM missing = registry.get("gpu-training-v1");
        System.out.println("  registry.get(...) returns " + missing + " -- no error yet");
        try {
            missing.clone();
        } catch (NullPointerException e) {
            System.out.println("  the crash comes later, where the null is used -> NullPointerException");
        }
    }
}
