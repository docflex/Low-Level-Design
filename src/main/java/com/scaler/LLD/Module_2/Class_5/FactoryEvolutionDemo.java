package com.scaler.LLD.Module_2.Class_5;

import com.scaler.LLD.Module_2.Class_5.Chat.AIChatClient;
import com.scaler.LLD.Module_2.Class_5.Chat.AIChatClientTemplate;
import com.scaler.LLD.Module_2.Class_5.Chat.AnthropicChatClient;
import com.scaler.LLD.Module_2.Class_5.Chat.OpenAIChatClient;
import com.scaler.LLD.Module_2.Class_5.Embeddings.AIVectorEmbeddingClient;
import com.scaler.LLD.Module_2.Class_5.Factory.AIClientFactory;
import com.scaler.LLD.Module_2.Class_5.Factory.FactoryProvider;
import com.scaler.LLD.Module_2.Class_5.Factory.Simple.AIChatClientFactory;
import com.scaler.LLD.Module_2.Class_5.Factory.Simple.AIVectorEmbeddingClientFactory;

import java.util.List;

/**
 * Every step from class, runnable side by side:
 *  1. Tight coupling            2. Dependency Injection       3. if-else in the client
 *  4. Simple Factory            5. Two Simple Factories (providers can be mixed)
 *  6. Abstract Factory          7. Abstract class for shared state (token count)
 *  8. Beyond the class: failover on every request instead of once at startup
 */
public class FactoryEvolutionDemo {

    /** Step 1: the service creates its own concrete client -- switching provider means editing it. */
    static class TightlyCoupledChatService {
        private final OpenAIChatClient client = new OpenAIChatClient();

        void chat(String prompt) {
            client.chat(prompt);
        }
    }

    /** Step 2: depends on the interface; the caller injects the implementation. */
    static class InjectedChatService {
        private final AIChatClient chatClient;

        InjectedChatService(AIChatClient chatClient) {
            this.chatClient = chatClient;
        }

        void chat(String prompt) {
            chatClient.chat(prompt);
        }
    }

    /** Step 3: the if-else copied into a client. */
    static InjectedChatService clientSideIfElse(String provider) {
        InjectedChatService chatService = null;
        if (provider.equalsIgnoreCase("OpenAI")) {
            chatService = new InjectedChatService(new OpenAIChatClient());
        } else if (provider.equalsIgnoreCase("Anthropic")) {
            chatService = new InjectedChatService(new AnthropicChatClient());
        }
        return chatService;
    }

    /** Step 7: a provider that inherits token tracking from the abstract class. */
    static class TrackedOpenAIChatClient extends AIChatClientTemplate {
        @Override
        public void chat(String prompt) {
            recordUsage(prompt);
            System.out.println("Servicing the request Via OpenAI (tracked)");
        }
    }

    /** Step 8 (beyond the class): a provider that fails after a number of successful calls. */
    static class FlakyChatClient implements AIChatClient {
        private final String name;
        private int callsLeft;

        FlakyChatClient(String name, int successfulCalls) {
            this.name = name;
            this.callsLeft = successfulCalls;
        }

        @Override
        public void chat(String prompt) {
            if (callsLeft-- <= 0) {
                throw new IllegalStateException(name + " is down");
            }
            System.out.println("Servicing the request Via " + name);
        }
    }

    /** Tries each client in order on EVERY request, so an outage mid-day is handled too. */
    static class FailoverChatClient implements AIChatClient {
        private final List<AIChatClient> clientsInPriorityOrder;

        FailoverChatClient(List<AIChatClient> clientsInPriorityOrder) {
            this.clientsInPriorityOrder = List.copyOf(clientsInPriorityOrder);
        }

        @Override
        public void chat(String prompt) {
            for (AIChatClient client : clientsInPriorityOrder) {
                try {
                    client.chat(prompt);
                    return;
                } catch (IllegalStateException e) {
                    System.out.println("  (" + e.getMessage() + ", trying next provider)");
                }
            }
            throw new IllegalStateException("All providers failed");
        }
    }

    public static void main(String[] args) {
        String prompt = "Hello World";

        System.out.println("=== Step 1: tight coupling ===");
        new TightlyCoupledChatService().chat(prompt);
        System.out.println("  to use Anthropic, TightlyCoupledChatService itself has to change");

        System.out.println("\n=== Step 2: Dependency Injection -- switching is a one-line change ===");
        new InjectedChatService(new OpenAIChatClient()).chat(prompt);
        new InjectedChatService(new AnthropicChatClient()).chat(prompt);

        System.out.println("\n=== Step 3: if-else in the client ===");
        clientSideIfElse("Anthropic").chat(prompt);
        try {
            clientSideIfElse("Gemini").chat(prompt);
        } catch (NullPointerException e) {
            System.out.println("  unknown provider \"Gemini\" -> chatService stays null -> NullPointerException");
        }

        System.out.println("\n=== Step 4: Simple Factory -- the if-else lives in one place ===");
        AIChatClientFactory.getAIChatClient("openai").chat(prompt);
        try {
            AIChatClientFactory.getAIChatClient("Gemini");
        } catch (IllegalArgumentException e) {
            System.out.println("  fails immediately with a clear message: " + e.getMessage());
        }

        System.out.println("\n=== Step 5: two independent Simple Factories -- nothing stops a mix-up ===");
        AIChatClient chat = AIChatClientFactory.getAIChatClient("OpenAI");
        AIVectorEmbeddingClient embedding = AIVectorEmbeddingClientFactory.getAIVectorEmbedding("Anthropic");
        embedding.embed(prompt);
        chat.chat(prompt);
        System.out.println("  compiles and runs -- but chat and embeddings now come from different providers");

        System.out.println("\n=== Step 6: Abstract Factory -- one choice, one matching family ===");
        for (String provider : List.of("OpenAI", "Anthropic")) {
            AIClientFactory factory = FactoryProvider.getClientFactory(provider);
            System.out.println("  " + provider + " -> " + factory.getClass().getSimpleName());
            factory.getAIVectorEmbeddingClient().embed(prompt);
            factory.getAIChatClient().chat(prompt);
        }

        System.out.println("\n=== Step 7: abstract class for shared state ===");
        TrackedOpenAIChatClient tracked = new TrackedOpenAIChatClient();
        AIChatClient asInterface = tracked;                 // still usable as an AIChatClient
        asInterface.chat("Which is the latest version of Apple?");
        asInterface.chat("Summarise the Factory pattern in one line");
        tracked.countTokensConsumed();

        System.out.println("\n=== Step 8 (beyond the class): failover on every request ===");
        AIChatClient failover = new FailoverChatClient(List.of(
                new FlakyChatClient("Anthropic", 1),        // works once, then goes down
                new FlakyChatClient("OpenAI", 10)));
        for (int request = 1; request <= 3; request++) {
            System.out.println("  request " + request + ":");
            failover.chat(prompt);
        }
    }
}
