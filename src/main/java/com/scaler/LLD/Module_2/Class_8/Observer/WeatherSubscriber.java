package com.scaler.LLD.Module_2.Class_8.Observer;

/** Observer: anything that wants to hear about weather changes. */
public interface WeatherSubscriber {
    void onWeatherUpdated();
}
