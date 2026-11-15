package com.scaler.LLD.Module_2.Class_5.spi;

import com.scaler.LLD.Module_2.Class_5.Factory.AIClientFactory;

/**
 * Service Provider Interface (SPI): the contract a provider team implements.
 * Implementations are listed in META-INF/services/com.scaler.LLD.Module_2.Class_5.spi.AIProvider
 * and discovered at runtime by java.util.ServiceLoader -- no registry code has to be edited.
 * Implementations need a public no-argument constructor.
 */
public interface AIProvider {
    String name();

    AIClientFactory factory();
}
