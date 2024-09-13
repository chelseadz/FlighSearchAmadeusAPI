package com.chelsea.FlightSearch.Models;

import com.chelsea.FlightSearch.Services.AmadeusService;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

public class Itinerary {
    private String departureAirport;
    private String arrivalAirport;
    private String departureAirportCity;
    private String arrivalAirportCity;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Duration duration;
    private Duration stopTime;
    private ArrayList<Segment> segments = new ArrayList<Segment>();

    public Itinerary(JSONObject itinerary, JSONObject flightObject) {
        duration = Duration.parse(itinerary.getString("duration"));
        Instant start, end;

        JSONArray segmentsArray = itinerary.getJSONArray("segments");
        JSONObject segmentObject;

        for (int l = 0; l < segmentsArray.length(); l++) {
            segmentObject = segmentsArray.getJSONObject(l);
            segments.add(new Segment(segmentObject, flightObject));
            if(l>0) {
                start = segments.get(l - 1).getArrivalTime().atZone(ZoneId.of("Europe/Paris")).toInstant();
                end = segments.get(l).getDepartureTime().atZone(ZoneId.of("Europe/Paris")).toInstant();
                stopTime = Duration.between(start, end);
            }
        }

        departureAirport = segments.getFirst().getDepartureAirport();
        departureTime = segments.getFirst().getDepartureTime();
        arrivalAirport = segments.getLast().getArrivalAirport();
        arrivalTime = segments.getLast().getArrivalTime();
        departureAirportCity = AmadeusService.getCity(departureAirport);
        arrivalAirportCity = AmadeusService.getCity(arrivalAirport);
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public ArrayList<Segment> getSegments() {
        return segments;
    }

    public Duration getStopTime() {
        return stopTime;
    }

    public String getDepartureAirportCity() {
        return departureAirportCity;
    }

    public String getArrivalAirportCity() {
        return arrivalAirportCity;
    }
}
