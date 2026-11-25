package com.scaler.LLD.Module_2.Class_7.Decorator.NotificationSystem;

/**
 * Decorator base class: holds the wrapped Notification once, so concrete decorators
 * (logging, retry, ...) only write their extra behaviour.
 */
public abstract class NotificationDecorator implements Notification {
    protected final Notification wrappedNotification;

    public NotificationDecorator(Notification wrappedNotification) {
        this.wrappedNotification = wrappedNotification;
    }
}
