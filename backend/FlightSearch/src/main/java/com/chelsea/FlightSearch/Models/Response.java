package com.chelsea.FlightSearch.Models;

import java.util.ArrayList;
import java.util.List;

public class Response {
    private int totalItems;
    private List<Flight> flights;

    public Response(int totalItems, List<Flight> flights) {
        this.totalItems = totalItems;
        this.flights = flights;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public List<Flight> getFlights() {
        return flights;
    }
}
