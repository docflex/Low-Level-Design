package com.scaler.LLD.Module_2.Class_5.Factory;

import com.scaler.LLD.Module_2.Class_5.Chat.AIChatClient;
import com.scaler.LLD.Module_2.Class_5.Embeddings.AIVectorEmbeddingClient;

/**
 * Abstract Factory: one interface with a factory method per product. Each implementation
 * (OpenAIFactory, AnthropicFactory) returns a matching family, so providers can't be mixed.
 */
public interface AIClientFactory {
    AIChatClient getAIChatClient();
    AIVectorEmbeddingClient getAIVectorEmbeddingClient();
}
