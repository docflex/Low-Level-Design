package com.scaler.LLD.Module_2.Class_5.Chat;

/**
 * Abstract class version of the chat client: use it when every provider needs shared STATE
 * (here, a running token count). An interface can't hold per-object state -- its fields are
 * always public static final constants.
 *
 * It implements AIChatClient, so subclasses can still be used anywhere an AIChatClient is expected.
 */
public abstract class AIChatClientTemplate implements AIChatClient {
    protected int tokensConsumed;

    public void countTokensConsumed() {
        System.out.println("Tokens Used: " + tokensConsumed);
    }

    public int getTokensConsumed() {
        return tokensConsumed;
    }

    /** Shared logic every provider gets for free: a rough token estimate (~1 token per 4 characters). */
    protected void recordUsage(String prompt) {
        tokensConsumed += Math.max(1, prompt.length() / 4);
    }

    @Override
    public abstract void chat(String prompt);
}
