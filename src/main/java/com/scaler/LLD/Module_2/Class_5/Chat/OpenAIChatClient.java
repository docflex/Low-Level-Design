package com.scaler.LLD.Module_2.Class_5.Chat;

public class OpenAIChatClient implements AIChatClient{

    @Override
    public void chat(String prompt) {
        System.out.println("Servicing the request Via OpenAI");
    }
}
