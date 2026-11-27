package com.scaler.LLD.Module_2.Class_8.Observer;

public class iPhone implements WeatherSubscriber {
    private final WeatherService weatherService;

    @Override
    public void onWeatherUpdated() {
        System.out.println("Updated Weather on iPhone: " + weatherService.getTemp() + " C");
    }

    public iPhone(WeatherService weatherService) {
        this.weatherService = weatherService;
        weatherService.subscribe(this);
    }
}
