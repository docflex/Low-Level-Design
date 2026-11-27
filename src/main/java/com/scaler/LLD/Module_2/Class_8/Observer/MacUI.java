package com.scaler.LLD.Module_2.Class_8.Observer;

public class MacUI implements WeatherSubscriber {
    private final WeatherService weatherService;

    @Override
    public void onWeatherUpdated() {
        System.out.println("Updated Weather on Mac UI: " + weatherService.getTemp() + " C");
    }

    public MacUI(WeatherService weatherService) {
        this.weatherService = weatherService;
        weatherService.subscribe(this);
    }
}
