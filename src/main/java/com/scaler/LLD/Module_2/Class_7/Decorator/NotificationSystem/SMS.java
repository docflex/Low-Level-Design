package com.scaler.LLD.Module_2.Class_7.Decorator.NotificationSystem;

public class SMS implements Notification {
    @Override
    public void send(String user, String message) {
        System.out.println("Sending Notif Via SMS from: " + user + " Message: " + message);
    }
}
