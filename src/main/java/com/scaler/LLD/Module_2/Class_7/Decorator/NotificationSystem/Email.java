package com.scaler.LLD.Module_2.Class_7.Decorator.NotificationSystem;

public class Email implements Notification {
    @Override
    public void send(String user, String message) {
        System.out.println("Sending Notif Via Email from: " + user + " Message: " + message);
    }
}
