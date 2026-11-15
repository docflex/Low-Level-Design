package com.scaler.LLD.Module_2.Class_5;

import com.scaler.LLD.Module_2.Class_5.Chat.AIChatClient;
import com.scaler.LLD.Module_2.Class_5.Embeddings.AIVectorEmbeddingClient;
import com.scaler.LLD.Module_2.Class_5.Factory.AIClientFactory;
import com.scaler.LLD.Module_2.Class_5.Factory.FactoryProvider;

import java.util.List;
import java.util.function.Predicate;

/**
 * Final version from class: Abstract Factory + failover.
 * Walks the providers in priority order and uses the first one that is up. Both clients come
 * from the SAME factory, so the chat and embedding providers always match.
 *
 * The provider is chosen once, when the service is created. If it goes down later, this service
 * keeps using it -- see FactoryEvolutionDemo for per-request failover.
 */
public class ChatService {
    private final AIChatClient chatClient;
    private final AIVectorEmbeddingClient vectorEmbeddingClient;
    private final String activeProvider;

    private static final List<String> DEFAULT_PROVIDERS = List.of("Anthropic", "OpenAI");

    /** Mocked health check: in production this would call each provider's status / health API. */
    private static boolean isProviderAvailable(String providerName) {
        if (providerName.equalsIgnoreCase("Anthropic")) {
            return false; // Anthropic is down
        } else if (providerName.equalsIgnoreCase("OpenAI")) {
            return true;  // OpenAI is up
        }
        return false;     // unknown providers are treated as unavailable
    }

    public ChatService() {
        this(DEFAULT_PROVIDERS, ChatService::isProviderAvailable);
    }

    /** Lets the demo try every up/down combination without editing the mock above. */
    public ChatService(List<String> providers, Predicate<String> isAvailable) {
        this.activeProvider = firstAvailable(providers, isAvailable);
        AIClientFactory clientFactory = FactoryProvider.getClientFactory(activeProvider);
        this.chatClient = clientFactory.getAIChatClient();
        this.vectorEmbeddingClient = clientFactory.getAIVectorEmbeddingClient();
    }

    private static String firstAvailable(List<String> providers, Predicate<String> isAvailable) {
        for (String provider : providers) {
            if (isAvailable.test(provider)) {
                return provider;
            }
        }
        throw new IllegalStateException("No Providers are Available. Tried: " + providers);
    }

    public void chat(String chatPrompt) {
        vectorEmbeddingClient.embed(chatPrompt);
        chatClient.chat(chatPrompt);
    }

    public String getActiveProvider() {
        return activeProvider;
    }
}
