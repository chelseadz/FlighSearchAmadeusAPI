package com.chelsea.FlightSearch.Models;
import com.chelsea.FlightSearch.Services.AmadeusService;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Segment {
    private String id;
    private String departureAirport;
    private String departureAirportCity;
    private LocalDateTime departureTime;
    private String arrivalAirport;
    private String arrivalAirportCity;
    private LocalDateTime arrivalTime;
    private String arrivalTerminal;
    private String departureTerminal;
    private String carrierCode;
    private String carrierCodeOperating;
    private String carrierName;
    private String carrierNameOperating;
    private String aircraftCode;
    private String aircraftName;
    private Duration duration;
    private String cabin;
    private String brandedFareLabel;
    private String fareClass;
    private boolean includedCheckedBags;
    private String checkedBagsWeight;
    private String weightUnit;
    private int checkedBagsQuantity;
    private ArrayList<String> amenities;

    public Segment(JSONObject segmentObject, JSONObject flightObject) {
        id = segmentObject.getString("id");
        departureAirport = segmentObject.getJSONObject("departure").getString("iataCode");
        departureTime = LocalDateTime.parse(segmentObject.getJSONObject("departure").getString("at"));
        arrivalAirport = segmentObject.getJSONObject("arrival").getString("iataCode");
        arrivalTime = LocalDateTime.parse(segmentObject.getJSONObject("arrival").getString("at"));
        arrivalTerminal = getStringFromJSON(segmentObject, "arrival", "terminal");
        departureTerminal = getStringFromJSON(segmentObject, "departure", "terminal");
        carrierCode = segmentObject.getString("carrierCode");
        carrierCodeOperating = getStringFromJSON(segmentObject, "operating", "carrierCode");
        aircraftCode = segmentObject.getJSONObject("aircraft").getString("code");
        duration = Duration.parse(segmentObject.getString("duration"));

        departureAirportCity = AmadeusService.getCity(departureAirport);
        arrivalAirportCity = AmadeusService.getCity(arrivalAirport);
        carrierName = AmadeusService.getAirline(carrierCode);
        carrierNameOperating = AmadeusService.getAirline(carrierCodeOperating);
        aircraftName = AmadeusService.getAircraft(aircraftCode);

        JSONObject json;
        JSONArray segments = flightObject.getJSONArray("travelerPricings").getJSONObject(0).getJSONArray("fareDetailsBySegment");
        for(int i = 0; i < segments.length(); i++) {
            json = segments.getJSONObject(i);
            if(json.getString("segmentId").equals(id)) {
                cabin = json.getString("cabin");
                brandedFareLabel = json.optString("brandedFareLabel");
                fareClass = json.getString("class");
                includedCheckedBags = (json.optJSONObject("includedCheckedBags") != null);
                checkedBagsQuantity = json.optJSONObject("includedCheckedBags").optInt("quantity");
                checkedBagsWeight = json.optJSONObject("includedCheckedBags").optString("weight");
                weightUnit = json.optJSONObject("includedCheckedBags").optString("weightUnit");
                amenities = new ArrayList<>();
                JSONArray amenitiesArray = json.optJSONArray("amenities");

                if (amenitiesArray != null) {
                    for (int j = 0; j < amenitiesArray.length(); j++) {
                        amenities.add(amenitiesArray.getJSONObject(j).getString("description"));
                    }
                }
            }
        }
    }

    private static String getStringFromJSON(JSONObject segmentObject, String objectStr, String valueStr) {
        try {
            return segmentObject.getJSONObject(objectStr).getString(valueStr);
        }catch (Exception e){
            return "";
        }
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public String getAircraftCode() {
        return aircraftCode;
    }

    public String getCarrierCodeOperating() {
        return carrierCodeOperating;
    }

    public String getCarrierCode() {
        return carrierCode;
    }

    public String getArrivalTerminal() {
        return arrivalTerminal;
    }

    public String getDepartureTerminal() {
        return departureTerminal;
    }

    public String getDepartureAirportCity() {
        return departureAirportCity;
    }

    public String getArrivalAirportCity() {
        return arrivalAirportCity;
    }

    public String getCabin() {
        return cabin;
    }

    public String getBrandedFareLabel() {
        return brandedFareLabel;
    }

    public String getFareClass() {
        return fareClass;
    }

    public ArrayList<String> getAmenities() {
        return amenities;
    }

    public String getId() {
        return id;
    }

    public boolean isIncludedCheckedBags() {
        return includedCheckedBags;
    }

    public String getCheckedBagsWeight() {
        return checkedBagsWeight;
    }

    public String getWeightUnit() {
        return weightUnit;
    }

    public int getCheckedBagsQuantity() {
        return checkedBagsQuantity;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public String getCarrierNameOperating() {
        return carrierNameOperating;
    }

    public String getAircraftName() {
        return aircraftName;
    }
}
