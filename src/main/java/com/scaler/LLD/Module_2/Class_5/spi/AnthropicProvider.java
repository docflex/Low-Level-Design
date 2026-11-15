package com.scaler.LLD.Module_2.Class_5.spi;

import com.scaler.LLD.Module_2.Class_5.Factory.AIClientFactory;
import com.scaler.LLD.Module_2.Class_5.Factory.AnthropicFactory;

public class AnthropicProvider implements AIProvider {
    public AnthropicProvider() {
        System.out.println("    [AnthropicProvider instantiated]");
    }

    @Override
    public String name() {
        return "Anthropic";
    }

    @Override
    public AIClientFactory factory() {
        return new AnthropicFactory();
    }
}
