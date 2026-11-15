package com.scaler.LLD.Module_2.Class_5.spi;

import com.scaler.LLD.Module_2.Class_5.Factory.AIClientFactory;
import com.scaler.LLD.Module_2.Class_5.Factory.OpenAIFactory;

public class OpenAIProvider implements AIProvider {
    public OpenAIProvider() {
        System.out.println("    [OpenAIProvider instantiated]");
    }

    @Override
    public String name() {
        return "OpenAI";
    }

    @Override
    public AIClientFactory factory() {
        return new OpenAIFactory();
    }
}
