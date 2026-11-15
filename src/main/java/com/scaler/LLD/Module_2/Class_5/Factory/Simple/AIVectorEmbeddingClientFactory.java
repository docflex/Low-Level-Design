package com.scaler.LLD.Module_2.Class_5.Factory.Simple;

import com.scaler.LLD.Module_2.Class_5.Embeddings.AIVectorEmbeddingClient;
import com.scaler.LLD.Module_2.Class_5.Embeddings.AnthropicEmbeddingClient;
import com.scaler.LLD.Module_2.Class_5.Embeddings.OpenAIEmbeddingClient;

/**
 * A second, independent Simple Factory. Nothing ties it to AIChatClientFactory, so a caller
 * can mix providers by accident -- the problem Abstract Factory solves.
 */
public class AIVectorEmbeddingClientFactory {
    public static AIVectorEmbeddingClient getAIVectorEmbedding(String providerName) {
        if (providerName.equalsIgnoreCase("OpenAI")) {
            return new OpenAIEmbeddingClient();
        } else if (providerName.equalsIgnoreCase("Anthropic")) {
            return new AnthropicEmbeddingClient();
        }
        throw new IllegalArgumentException("Unknown Provider: " + providerName);
    }
}
