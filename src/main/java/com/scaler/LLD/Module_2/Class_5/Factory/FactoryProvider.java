package com.scaler.LLD.Module_2.Class_5.Factory;

import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Picks the right Abstract Factory for a provider name -- using a Registry instead of an if-else,
 * so a new provider can be added by calling register(...) from its own code (OCP).
 *
 * - Names are case-insensitive, like the if-else version (equalsIgnoreCase) it replaced
 * - ConcurrentHashMap: providers can be registered at runtime while requests are being served
 * - Unknown name: IllegalArgumentException that lists the registered providers
 */
public class FactoryProvider {
    private static final Map<String, AIClientFactory> registry = new ConcurrentHashMap<>();

    static {
        register("OpenAI", new OpenAIFactory());
        register("Anthropic", new AnthropicFactory());
    }

    private FactoryProvider() {
    }

    public static void register(String providerName, AIClientFactory factory) {
        registry.put(key(providerName), factory);
    }

    public static AIClientFactory getClientFactory(String providerName) {
        AIClientFactory aiClientFactory = registry.get(key(providerName));

        if (aiClientFactory == null) {
            throw new IllegalArgumentException("Unknown Provider: " + providerName + ". Registered: " + providers());
        }

        return aiClientFactory;
    }

    public static Set<String> providers() {
        return new TreeSet<>(registry.keySet());
    }

    private static String key(String providerName) {
        return providerName.toLowerCase(Locale.ROOT);
    }
}
