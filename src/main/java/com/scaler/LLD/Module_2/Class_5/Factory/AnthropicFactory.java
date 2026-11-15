package com.scaler.LLD.Module_2.Class_5.Factory;

import com.scaler.LLD.Module_2.Class_5.Chat.AIChatClient;
import com.scaler.LLD.Module_2.Class_5.Chat.AnthropicChatClient;
import com.scaler.LLD.Module_2.Class_5.Embeddings.AIVectorEmbeddingClient;
import com.scaler.LLD.Module_2.Class_5.Embeddings.AnthropicEmbeddingClient;

public class AnthropicFactory implements AIClientFactory {
    @Override
    public AIChatClient getAIChatClient() {
        return new AnthropicChatClient();
    }

    @Override
    public AIVectorEmbeddingClient getAIVectorEmbeddingClient() {
        return new AnthropicEmbeddingClient();
    }
}
