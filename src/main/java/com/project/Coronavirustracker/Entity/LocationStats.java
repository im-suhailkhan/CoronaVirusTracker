package com.project.Coronavirustracker.Entity;

public class LocationStats {
    private String state;
    private String country;
    private int totalCases;
    private int deltaCases;

    public int getDeltaCases() {
        return deltaCases;
    }

    public void setDeltaCases(int deltaCases) {
        this.deltaCases = deltaCases;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(int totalCases) {
        this.totalCases = totalCases;
    }
}
