package com.scaler.LLD.Module_2.Class_7.Decorator.NotificationSystem;

/** Decorator with notifications: add logging and retry to any channel by wrapping it. */
public class Client {

    /** A channel whose gateway times out a given number of times before it succeeds. */
    static class FlakySMS implements Notification {
        private int failuresLeft;

        FlakySMS(int failuresBeforeSuccess) {
            this.failuresLeft = failuresBeforeSuccess;
        }

        @Override
        public void send(String user, String message) {
            if (failuresLeft-- > 0) {
                throw new IllegalStateException("SMS gateway timeout");
            }
            new SMS().send(user, message);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 1. Logging only for email ===");
        Notification emailNotif = new LoggingDecorator(new Email());
        emailNotif.send("Rehber", "Hello");
        Notification smsNotif = new SMS();
        smsNotif.send("Nehal", "Hi");

        System.out.println("\n=== 2. Logging for SMS too -- just wrap it ===");
        new LoggingDecorator(new SMS()).send("Nehal", "Hi");

        System.out.println("\n=== 3. Retry around logging: the gateway fails twice, then succeeds ===");
        new RetryDecorator(new LoggingDecorator(new FlakySMS(2))).send("Nehal", "OTP 4821");

        System.out.println("\n=== 4. Order matters: logging around retry logs only once ===");
        new LoggingDecorator(new RetryDecorator(new FlakySMS(2))).send("Nehal", "OTP 4821");

        System.out.println("\n=== 5. Every attempt fails: the error still reaches the caller ===");
        try {
            new RetryDecorator(new FlakySMS(5), 3).send("Nehal", "OTP 4821");
        } catch (IllegalStateException e) {
            System.out.println("caller sees: " + e.getMessage());
        }
    }
}
