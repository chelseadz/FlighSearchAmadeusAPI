package com.chelsea.FlightSearch.Models.DTOs;

import org.json.JSONObject;

public class FlightDTO {
    private String departureAirport;
    private String arrivalAirport;
    private String departureDate;
    private String returnDate;
    private String currency;
    private boolean nonStop;
    private int adults;

    public FlightDTO(String FlightStr) {
        JSONObject flightObject = new JSONObject(FlightStr);
        flightObject = flightObject.getJSONObject("params");
        departureAirport = flightObject.getString("departureAirport");
        arrivalAirport = flightObject.getString("arrivalAirport");
        departureDate = flightObject.getString("departureDate");
        returnDate = flightObject.getString("returnDate");
        currency = flightObject.getString("currency");
        nonStop = flightObject.getBoolean("nonStop");
        adults = flightObject.getInt("adults");
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public String getCurrency() {
        return currency;
    }

    public boolean isNonStop() {
        return nonStop;
    }


    public int getAdults() {
        return adults;
    }
}
