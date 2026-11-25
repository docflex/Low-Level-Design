package com.scaler.LLD.Module_2.Class_7.Decorator.NotificationSystem;

public class LoggingDecorator extends NotificationDecorator {
    public LoggingDecorator(Notification wrappedNotification) {
        super(wrappedNotification);
    }

    @Override
    public void send(String user, String message) {
        System.out.println("[LOG]: Sending Message to " + user);
        wrappedNotification.send(user, message);
    }
}
