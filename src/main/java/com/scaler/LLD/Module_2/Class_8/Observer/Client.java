package com.scaler.LLD.Module_2.Class_8.Observer;

/** Observer with weather: devices are pushed every change instead of polling for it. */
public class Client {
    public static void main(String[] args) {
        WeatherService weatherService = new WeatherService(10);
        iPhone iPhone = new iPhone(weatherService);
        MacUI macUI = new MacUI(weatherService);

        System.out.println("=== Two updates, two subscribers ===");
        weatherService.updateTemp();
        weatherService.updateTemp();

        System.out.println("\n=== iPhone unsubscribes ===");
        weatherService.unsubscribe(iPhone);
        weatherService.updateTemp();
    }
}
