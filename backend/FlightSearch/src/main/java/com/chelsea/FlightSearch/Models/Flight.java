package com.chelsea.FlightSearch.Models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Flight{
    private String id;
    private int numberOfBookableSeats;
    private ArrayList<Itinerary> itineraries = new ArrayList<Itinerary>();
    private Price price;
    private int travelers;

    public Flight(JSONObject flightObject) {
        JSONObject itinerary;

        id = flightObject.getString("id");
        JSONObject priceObject = flightObject.getJSONObject("price");
        price = new Price(priceObject);
        numberOfBookableSeats = flightObject.getInt("numberOfBookableSeats");
        travelers = flightObject.getJSONArray("travelerPricings").length();

        JSONArray itinerariesArray = flightObject.getJSONArray("itineraries");
        for (int j = 0; j < itinerariesArray.length(); j++) {
            itinerary = itinerariesArray.getJSONObject(j);
            itineraries.add(new Itinerary(itinerary, flightObject));
        }

    }

    public String getId() {
        return id;
    }

    public int getNumberOfBookableSeats() {
        return numberOfBookableSeats;
    }

    public List<Itinerary> getItineraries() {
        return itineraries;
    }

    public Price getPrice() {
        return price;
    }

    public int getTravelers() {
        return travelers;
    }
}
