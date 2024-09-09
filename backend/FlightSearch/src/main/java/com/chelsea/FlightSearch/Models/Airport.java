package com.chelsea.FlightSearch.Models;

public class Airport {
    private String iata;
    private String name;

    public Airport(String iata, String name) {
        this.iata = iata;
        this.name = name;
    }

    public String getIata() {
        return this.iata;
    }

    public String getName() {
        return name;
    }
}
