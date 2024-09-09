package com.chelsea.FlightSearch.Models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Traveler {
    private String travelerId;
    private String travelerType;
    private String totalPrice;
    private ArrayList<FareDetailsBySegment> fareDetailsBySegment;

    public Traveler(JSONObject jsonObject) {
        JSONArray fareDetails;
        travelerId = jsonObject.getString("travelerId");
        travelerType = jsonObject.getString("travelerType");
        totalPrice = jsonObject.getJSONObject("price").getString("total");

        fareDetailsBySegment = new ArrayList<>();
        fareDetails = jsonObject.getJSONArray("fareDetailsBySegment");
        for (int i = 0; i < fareDetails.length(); i++) {
            fareDetailsBySegment.add(new FareDetailsBySegment(fareDetails.getJSONObject(i)));
        }
    }

    public String getTravelerId() {
        return travelerId;
    }

    public String getTravelerType() {
        return travelerType;
    }

    public String getPrice() {
        return totalPrice;
    }

    public ArrayList<FareDetailsBySegment> getFareDetailsBySegment() {
        return fareDetailsBySegment;
    }
}
