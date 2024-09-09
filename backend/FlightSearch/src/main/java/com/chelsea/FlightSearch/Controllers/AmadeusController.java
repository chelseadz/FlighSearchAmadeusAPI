package com.chelsea.FlightSearch.Controllers;

import com.chelsea.FlightSearch.Models.*;
import com.chelsea.FlightSearch.Models.DTOs.FlightDTO;
import com.chelsea.FlightSearch.Services.AmadeusService;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.*;

@CrossOrigin(origins = "*")
@RestController
public class AmadeusController {

    private final AmadeusService amadeusService;
    ArrayList<Flight> flights = new ArrayList<Flight>();
    private final int pageSize = 10;

    public AmadeusController(AmadeusService amadeusService) {
        this.amadeusService = amadeusService;
    }

    @GetMapping("/pagination")
    public List<Flight> getFlights(@RequestParam int page) {
        int fromIndex = pageSize * (page - 1);
        int toIndex = Math.min(fromIndex + pageSize, flights.size());
        return flights.subList(fromIndex, toIndex);
    }

    @GetMapping("/sorted-pagination")
    public List<Flight> getFlights(@RequestParam int page, @RequestParam String value, @RequestParam boolean checked) {
        if (Objects.equals(value, "price")) {
            flights.sort((f1, f2) -> sortByPrice(f1).compareTo(sortByPrice(f2)));
        }

        if (Objects.equals(value, "duration")) {
            flights.sort((f1, f2) -> sortByDuration(f1).compareTo(sortByDuration(f2)));
        }

        int fromIndex = pageSize * (page - 1);
        int toIndex = Math.min(fromIndex + pageSize, flights.size());
        return flights.subList(fromIndex, toIndex);
    }

    private Float sortByPrice(Flight f1) {
        return f1.getPrice().getTotal();
    }

    private Duration sortByDuration(Flight f1) {
        Duration duration = Duration.ZERO;
        for(Itinerary itinerary : f1.getItineraries()) {
            duration = duration.plus(itinerary.getDuration());
        }
        return duration;
    }

    @PostMapping("/flight-offers")
    public Response getFlightOffers(@RequestBody String flightStr) {
        FlightDTO flightDTO = new FlightDTO(flightStr);
        String response = amadeusService.getFlightOffers(flightDTO);
        JSONObject jsonObject = new JSONObject(response);
        JSONArray dataArray = jsonObject.getJSONArray("data");
        JSONObject flightObject;

        flights.clear();

        for (int i = 0; i < dataArray.length(); i++) {
            flightObject = dataArray.getJSONObject(i);
            Flight flight = new Flight(flightObject);
            flights.add(flight);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("totalItems", String.valueOf(flights.size()));

        return new Response(flights.size(), flights.subList(0,Math.min(10, flights.size())));
    }

    @GetMapping("/airports")
    public ArrayList<Airport>  airports(@RequestParam String keyword) {
        String response = amadeusService.getAirports(keyword.toUpperCase());
        JSONObject jsonObject = new JSONObject(response);
        JSONArray dataArray = jsonObject.getJSONArray("data");
        JSONObject airportObject;

        ArrayList<Airport> airports = new ArrayList<Airport>();

        for (int i = 0; i < dataArray.length(); i++) {
            airportObject = dataArray.getJSONObject(i);
            Airport airport = new Airport(airportObject.getString("iataCode"), airportObject.getString("name"));
            airports.add(airport);
        }

        return airports;
    }

}

