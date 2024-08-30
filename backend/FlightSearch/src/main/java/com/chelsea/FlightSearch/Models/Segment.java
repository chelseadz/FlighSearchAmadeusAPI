package com.chelsea.FlightSearch.Models;
import java.time.LocalDateTime;

public class Segment {
    private String departureCode;
    private LocalDateTime departureTime;
    private String arrivalCode;
    private LocalDateTime arrivalTime;
    private String carrierCode;
    private String number;
    private String aircraftCode;
    private String duration;
    private String id;
    private String cabin;
    private String fareBasis;
    private String flightClass;
    private int numberOfStops;
    private boolean blacklistedInEU;

    public String getDepartureCode() {
        return departureCode;
    }

    public void setDepartureCode(String departureCode) {
        this.departureCode = departureCode;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalCode() {
        return arrivalCode;
    }

    public void setArrivalCode(String arrivalCode) {
        this.arrivalCode = arrivalCode;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getCarrierCode() {
        return carrierCode;
    }

    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAircraftCode() {
        return aircraftCode;
    }

    public void setAircraftCode(String aircraftCode) {
        this.aircraftCode = aircraftCode;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCabin() {
        return cabin;
    }

    public void setCabin(String cabin) {
        this.cabin = cabin;
    }

    public String getFareBasis() {
        return fareBasis;
    }

    public void setFareBasis(String fareBasis) {
        this.fareBasis = fareBasis;
    }

    public String getFlightClass() {
        return flightClass;
    }

    public void setFlightClass(String flightClass) {
        this.flightClass = flightClass;
    }

    public int getNumberOfStops() {
        return numberOfStops;
    }

    public void setNumberOfStops(int numberOfStops) {
        this.numberOfStops = numberOfStops;
    }

    public boolean isBlacklistedInEU() {
        return blacklistedInEU;
    }

    public void setBlacklistedInEU(boolean blacklistedInEU) {
        this.blacklistedInEU = blacklistedInEU;
    }

    public int getIncludedCheckedBags() {
        return includedCheckedBags;
    }

    public void setIncludedCheckedBags(int includedCheckedBags) {
        this.includedCheckedBags = includedCheckedBags;
    }

    private int includedCheckedBags;
}
