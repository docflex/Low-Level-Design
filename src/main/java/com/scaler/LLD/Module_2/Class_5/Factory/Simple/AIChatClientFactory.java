package com.scaler.LLD.Module_2.Class_5.Factory.Simple;

import com.scaler.LLD.Module_2.Class_5.Chat.AIChatClient;
import com.scaler.LLD.Module_2.Class_5.Chat.AnthropicChatClient;
import com.scaler.LLD.Module_2.Class_5.Chat.OpenAIChatClient;

/**
 * Simple Factory: one static method that picks the concrete class from an input.
 * The if-else still exists, but in ONE place instead of in every client.
 */
public class AIChatClientFactory {
    public static AIChatClient getAIChatClient(String providerName) {
        if (providerName.equalsIgnoreCase("OpenAI")) {
            return new OpenAIChatClient();
        } else if (providerName.equalsIgnoreCase("Anthropic")) {
            return new AnthropicChatClient();
        }
        throw new IllegalArgumentException("Unknown Provider: " + providerName);
    }
}
