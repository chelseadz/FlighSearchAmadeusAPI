package com.chelsea.FlightSearch.Models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class FareDetailsBySegment {
    private String segmentId;
    private String cabin;
    private String brandedFareLabel;
    private String fareClass;
    private int includedCheckedBags;
    private ArrayList<String> amenities;

    public FareDetailsBySegment(JSONObject jsonObject) {
        segmentId = jsonObject.getString("segmentId");
        cabin = jsonObject.getString("cabin");
        brandedFareLabel = jsonObject.optString("brandedFareLabel");
        fareClass = jsonObject.getString("class");
        includedCheckedBags = jsonObject.optJSONObject("includedCheckedBags").optInt("quantity");
        amenities = new ArrayList<>();
        JSONArray amenitiesArray = jsonObject.optJSONArray("amenities");

        if (amenitiesArray != null) {
            for (int i = 0; i < amenitiesArray.length(); i++) {
                amenities.add(amenitiesArray.getJSONObject(i).getString("description"));
            }
        }
    }

    public String getSegmentId() {
        return segmentId;
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

    public int getIncludedCheckedBags() {
        return includedCheckedBags;
    }

    public ArrayList<String> getAmenities() {
        return amenities;
    }
}
