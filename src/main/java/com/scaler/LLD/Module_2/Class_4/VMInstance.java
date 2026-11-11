package com.scaler.LLD.Module_2.Class_4;

/**
 * Prototype: clone() copies every field through the copy constructor.
 * os / runtime / monitoringAgent are the template part (final);
 * hostname / ipAddress change per machine (setters).
 */
public class VMInstance implements Prototype<VMInstance> {
    private final String os;
    private final String runtime;
    private final String monitoringAgent;
    private String hostname;
    private String ipAddress;

    public VMInstance(String os, String runtime, String monitoringAgent, String hostname, String ipAddress) {
        this.os = os;
        this.runtime = runtime;
        this.monitoringAgent = monitoringAgent;
        this.hostname = hostname;
        this.ipAddress = ipAddress;
    }

    /**
     * Protected so clients copy through clone(): new VMInstance(gpuVm) would compile
     * and silently produce a plain VMInstance without the GPU fields.
     */
    protected VMInstance(VMInstance o) {
        this.os = o.os;
        this.runtime = o.runtime;
        this.monitoringAgent = o.monitoringAgent;
        this.hostname = o.hostname;
        this.ipAddress = o.ipAddress;
    }

    @Override
    public VMInstance clone() {
        return new VMInstance(this);
    }

    public String getOs() {
        return os;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getMonitoringAgent() {
        return monitoringAgent;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{os='" + os + "', runtime='" + runtime + "', monitoringAgent='"
                + monitoringAgent + "', hostname=" + hostname + ", ipAddress=" + ipAddress + extraFields() + "}";
    }

    /** Lets subclasses add their own fields to toString(). */
    protected String extraFields() {
        return "";
    }
}
