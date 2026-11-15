package com.scaler.LLD.Module_2.Class_5.spi;

import com.scaler.LLD.Module_2.Class_5.Factory.AIClientFactory;
import com.scaler.LLD.Module_2.Class_5.Factory.AnthropicFactory;
import com.scaler.LLD.Module_2.Class_5.Factory.OpenAIFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Homework: Supplier and Java's Service Provider Interface (SPI).
 *  1. Supplier<T>: "something that can produce a T later" -- a constructor reference is one
 *  2. ServiceLoader finds providers listed in META-INF/services, without hard-coding them
 *  3. Each ServiceLoader.Provider IS a Supplier: type() needs no object, get() creates it
 */
public class SpiDemo {
    public static void main(String[] args) {
        System.out.println("=== 1. Supplier registry: store HOW to create, create on demand ===");
        Map<String, Supplier<AIClientFactory>> registry = new HashMap<>();
        registry.put("openai", OpenAIFactory::new);          // constructor reference = Supplier
        registry.put("anthropic", AnthropicFactory::new);
        System.out.println("  registered " + registry.keySet() + " -- no factory created yet");
        AIClientFactory f1 = registry.get("openai").get();
        AIClientFactory f2 = registry.get("openai").get();
        System.out.println("  get() twice -> two separate objects? " + (f1 != f2));

        System.out.println("\n=== 2. ServiceLoader: discover providers from META-INF/services ===");
        ServiceLoader<AIProvider> loader = ServiceLoader.load(AIProvider.class);
        List<ServiceLoader.Provider<AIProvider>> found = loader.stream().collect(Collectors.toList());
        System.out.println("  found " + found.size() + " providers (by class only, nothing instantiated yet):");
        for (ServiceLoader.Provider<AIProvider> provider : found) {
            System.out.println("    " + provider.type().getSimpleName());
        }

        System.out.println("\n=== 3. Provider is a Supplier: get() instantiates ===");
        Map<String, AIClientFactory> factories = new HashMap<>();
        for (ServiceLoader.Provider<AIProvider> provider : found) {
            Supplier<AIProvider> supplier = provider;        // compiles: Provider<S> extends Supplier<S>
            AIProvider aiProvider = supplier.get();
            factories.put(aiProvider.name().toLowerCase(Locale.ROOT), aiProvider.factory());
        }
        System.out.println("  registry built from SPI: " + factories.keySet());

        System.out.println("\n=== 4. Use the provider the other team added (no existing code edited) ===");
        AIClientFactory gemini = factories.get("gemini");
        gemini.getAIVectorEmbeddingClient().embed("Hello");
        gemini.getAIChatClient().chat("Hello");
    }
}
