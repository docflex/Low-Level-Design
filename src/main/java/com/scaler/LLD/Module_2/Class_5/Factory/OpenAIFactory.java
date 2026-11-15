package com.scaler.LLD.Module_2.Class_5.Factory;

import com.scaler.LLD.Module_2.Class_5.Chat.AIChatClient;
import com.scaler.LLD.Module_2.Class_5.Chat.OpenAIChatClient;
import com.scaler.LLD.Module_2.Class_5.Embeddings.AIVectorEmbeddingClient;
import com.scaler.LLD.Module_2.Class_5.Embeddings.OpenAIEmbeddingClient;

public class OpenAIFactory implements AIClientFactory {
    @Override
    public AIChatClient getAIChatClient() {
        return new OpenAIChatClient();
    }

    @Override
    public AIVectorEmbeddingClient getAIVectorEmbeddingClient() {
        return new OpenAIEmbeddingClient();
    }
}
