package com.scaler.LLD.Module_2.Class_6.logging;

/** Target interface: what our log service wants every destination to look like. */
public interface LogSink {
    String name();

    void write(LogLevel level, String message);
}
