package com.scaler.LLD.Module_2.Class_8.Observer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Subject: keeps a list of subscribers and notifies them when the temperature changes.
 * Subscribers read the new value through getTemp().
 */
public class WeatherService {
    private int temp;

    // Copy-on-write: a subscriber may unsubscribe while we're notifying, without breaking the loop
    private final List<WeatherSubscriber> subscribers;

    public void subscribe(WeatherSubscriber weatherSubscriber) {
        if (!subscribers.contains(weatherSubscriber)) {
            subscribers.add(weatherSubscriber);
        }
    }

    public void unsubscribe(WeatherSubscriber weatherSubscriber) {
        subscribers.remove(weatherSubscriber);
    }

    public WeatherService(int temp) {
        this.temp = temp;
        this.subscribers = new CopyOnWriteArrayList<>();
    }

    public int getTemp() {
        return temp;
    }

    public void updateTemp() {
        temp += 5;
        notifySubscribers();
    }

    /** Private: only a real change should notify anyone. */
    private void notifySubscribers() {
        for (WeatherSubscriber weatherSubscriber : subscribers) {
            weatherSubscriber.onWeatherUpdated();
        }
    }
}
