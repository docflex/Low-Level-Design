package com.scaler.LLD.Module_2.Class_7.Decorator.NotificationSystem;

/**
 * Retries the wrapped notification when it throws, up to maxAttempts times in total.
 * If every attempt fails, the last exception is re-thrown to the caller.
 */
public class RetryDecorator extends NotificationDecorator {
    private final int maxAttempts;

    public RetryDecorator(Notification wrappedNotification) {
        this(wrappedNotification, 3);
    }

    public RetryDecorator(Notification wrappedNotification, int maxAttempts) {
        super(wrappedNotification);
        if (maxAttempts < 1) {
            throw new IllegalArgumentException("maxAttempts must be at least 1");
        }
        this.maxAttempts = maxAttempts;
    }

    @Override
    public void send(String user, String message) {
        for (int attempt = 1; ; attempt++) {
            try {
                System.out.println("[RETRY]: attempt " + attempt + " of " + maxAttempts);
                wrappedNotification.send(user, message);
                return;
            } catch (RuntimeException e) {
                if (attempt == maxAttempts) {
                    System.out.println("[RETRY]: giving up -- " + e.getMessage());
                    throw e;
                }
                System.out.println("[RETRY]: failed (" + e.getMessage() + "), retrying");
            }
        }
    }
}
