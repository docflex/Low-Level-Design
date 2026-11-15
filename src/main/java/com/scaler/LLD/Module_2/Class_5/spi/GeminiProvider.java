package com.scaler.LLD.Module_2.Class_5.spi;

import com.scaler.LLD.Module_2.Class_5.Chat.AIChatClient;
import com.scaler.LLD.Module_2.Class_5.Embeddings.AIVectorEmbeddingClient;
import com.scaler.LLD.Module_2.Class_5.Factory.AIClientFactory;

/**
 * Written by "another team": a brand-new provider. Adding it needed this class plus one line
 * in the META-INF/services file -- no existing Java code changed.
 */
public class GeminiProvider implements AIProvider {
    public GeminiProvider() {
        System.out.println("    [GeminiProvider instantiated]");
    }

    @Override
    public String name() {
        return "Gemini";
    }

    @Override
    public AIClientFactory factory() {
        return new AIClientFactory() {
            @Override
            public AIChatClient getAIChatClient() {
                return prompt -> System.out.println("Servicing the request Via Gemini");
            }

            @Override
            public AIVectorEmbeddingClient getAIVectorEmbeddingClient() {
                return prompt -> System.out.println("Embedding From Gemini");
            }
        };
    }
}
