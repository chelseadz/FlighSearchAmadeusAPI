package com.chelsea.FlightSearch.Controllers;

import com.chelsea.FlightSearch.Models.Flight;
import com.chelsea.FlightSearch.Services.AmadeusService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Collection;

@CrossOrigin(origins = "*")
@RestController
public class AmadeusController {

    private final AmadeusService amadeusService;

    public AmadeusController(AmadeusService amadeusService) {
        this.amadeusService = amadeusService;
    }

    @GetMapping("/flight-offers")
    public Collection<Flight> getFlightOffers() {
        try {
            amadeusService.getFlightOffers();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @GetMapping("/auth")
    public String auth() {
        String token = amadeusService.getAuthToken();
        return token;
    }
}

