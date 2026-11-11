package com.scaler.LLD.Module_2.Class_4;

/**
 * Child prototype: overrides clone() so copies keep gpuType.
 * Forgetting the override would make clone() return a plain VMInstance (Quiz 1).
 */
public class GPU_VMInstance extends VMInstance {

    private final String gpuType;

    public GPU_VMInstance(String os, String runtime, String monitoringAgent, String hostname, String ipAddress, String gpuType) {
        super(os, runtime, monitoringAgent, hostname, ipAddress);
        this.gpuType = gpuType;
    }

    protected GPU_VMInstance(GPU_VMInstance o) {
        super(o);
        this.gpuType = o.gpuType;
    }

    /** Builds a GPU template on top of an existing base template (used by prefillRegistry). */
    public GPU_VMInstance(VMInstance o, String gpuType) {
        super(o);
        this.gpuType = gpuType;
    }

    /** Covariant return: callers holding a GPU_VMInstance get one back without a cast. */
    @Override
    public GPU_VMInstance clone() {
        return new GPU_VMInstance(this);
    }

    public String getGpuType() {
        return gpuType;
    }

    @Override
    protected String extraFields() {
        return ", gpuType='" + gpuType + "'";
    }
}
