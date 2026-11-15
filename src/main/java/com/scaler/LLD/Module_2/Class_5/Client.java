package com.scaler.LLD.Module_2.Class_5;

import com.scaler.LLD.Module_2.Class_5.Chat.AIChatClient;
import com.scaler.LLD.Module_2.Class_5.Embeddings.AIVectorEmbeddingClient;
import com.scaler.LLD.Module_2.Class_5.Factory.AIClientFactory;
import com.scaler.LLD.Module_2.Class_5.Factory.FactoryProvider;

import java.util.List;
import java.util.Map;
import java.util.Set;

/** Final version: Abstract Factory chosen through the FactoryProvider registry, with failover. */
public class Client {
    public static void main(String[] args) {
        String prompt = "Which is the latest version of Apple?";

        System.out.println("=== 1. Default failover (Anthropic down, OpenAI up) ===");
        ChatService chatService = new ChatService();
        System.out.println("  active provider: " + chatService.getActiveProvider());
        chatService.chat(prompt);

        System.out.println("\n=== 2. Every up/down combination (priority: Anthropic, then OpenAI) ===");
        List<String> priority = List.of("Anthropic", "OpenAI");
        Map<String, Set<String>> scenarios = Map.of(
                "both up", Set.of("Anthropic", "OpenAI"),
                "Anthropic down", Set.of("OpenAI"),
                "OpenAI down", Set.of("Anthropic"),
                "both down", Set.of());
        for (String scenario : List.of("both up", "Anthropic down", "OpenAI down", "both down")) {
            Set<String> up = scenarios.get(scenario);
            try {
                ChatService service = new ChatService(priority, up::contains);
                System.out.println("  " + scenario + " -> uses " + service.getActiveProvider());
            } catch (IllegalStateException e) {
                System.out.println("  " + scenario + " -> " + e.getMessage());
            }
        }

        System.out.println("\n=== 3. A new provider registered from outside FactoryProvider (OCP) ===");
        FactoryProvider.register("Gemini", new AIClientFactory() {
            @Override
            public AIChatClient getAIChatClient() {
                return p -> System.out.println("Servicing the request Via Gemini");
            }

            @Override
            public AIVectorEmbeddingClient getAIVectorEmbeddingClient() {
                return p -> System.out.println("Embedding From Gemini");
            }
        });
        System.out.println("  registered providers: " + FactoryProvider.providers());
        new ChatService(List.of("Gemini"), name -> true).chat(prompt);

        System.out.println("\n=== 4. Provider names are case-insensitive; unknown names fail clearly ===");
        System.out.println("  \"openai\" -> " + FactoryProvider.getClientFactory("openai").getClass().getSimpleName());
        try {
            FactoryProvider.getClientFactory("Mistral");
        } catch (IllegalArgumentException e) {
            System.out.println("  \"Mistral\" -> " + e.getMessage());
        }
    }
}
