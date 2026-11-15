package com.scaler.LLD.Module_2.Class_5.Embeddings;

public class AnthropicEmbeddingClient implements AIVectorEmbeddingClient{
    @Override
    public void embed(String prompt) {
        System.out.println("Embedding From Anthropic");
    }
}
