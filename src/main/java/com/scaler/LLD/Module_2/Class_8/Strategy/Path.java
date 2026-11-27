package com.scaler.LLD.Module_2.Class_8.Strategy;

public class Path {
    private int distance;
    private double etaInHours;

    public Path(int distance, double etaInHours) {
        this.distance = distance;
        this.etaInHours = etaInHours;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public double getEtaInHours() {
        return etaInHours;
    }

    public void setEtaInHours(double etaInHours) {
        this.etaInHours = etaInHours;
    }
}
