package com.chelsea.FlightSearch.Services;


import com.chelsea.FlightSearch.Models.DTOs.FlightDTO;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.json.JSONObject;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClientRequest;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;

import static org.springframework.web.reactive.function.BodyInserters.fromFormData;


@Service
public class AmadeusService {

    @Value("${clientId}")
    private String clientId;

    @Value("${clientSecret}")
    private String clientSecret;

    @Value("${amadeus.url}")
    private String baseURL;

    public String getAccessToken() {
        return accessToken;
    }
    private static String accessToken;
    private static Instant lastTokenRefresh = Instant.MIN;
    private static HashMap<String, String> cityMemory = new HashMap<>();
    private static JSONObject aircrafts;
    private static JSONObject carriers;

    public String getAuthToken() {
        String url = "https://test.api.amadeus.com/v1/security/oauth2/token";
        try {
            String response = WebClient.create().post()
                    .uri(url)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(fromFormData("grant_type", "client_credentials")
                            .with("client_id", clientId)
                            .with("client_secret", clientSecret))
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            clientResponse -> clientResponse.bodyToMono(String.class)
                                    .flatMap(errorBody -> {
                                        return Mono.error(new RuntimeException("API Error: " + errorBody));
                                    }))
                    .bodyToMono(String.class)
                    .block();
            JSONObject json = new JSONObject(response);
            accessToken = json.getString("access_token");
            lastTokenRefresh = Instant.now();
            return "success";
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return (e.getMessage());
        }
    }

    private static WebClient webClient() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Access", "application/vnd.amadeus+json");
        headers.set("Authorization", "Bearer %s".formatted(accessToken));
        final int size = 16 * 1024 * 1024;
        final ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
                .build();
        return WebClient
                .builder()
                .exchangeStrategies(strategies)
                .defaultHeaders(h -> h.addAll(headers))
                .build();
    }

    public String getAirports(String keyword) {
        String url = UriComponentsBuilder.newInstance()
                .host("test.api.amadeus.com/v1")
                .scheme("https")
                .path("/reference-data/locations")
                .queryParam("subType", "AIRPORT")
                .queryParam("keyword", keyword.toUpperCase())
                .queryParam("page[limit]", 10)
                .queryParam("page[offset]", 0)
                .queryParam("view", "LIGHT")
                .build().toUriString();
        verifyToken();
        try{
            String response = webClient().get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(String.class)
                    .retryWhen(Retry.backoff(5, Duration.ofSeconds(1))
                            .maxBackoff(Duration.ofSeconds(8))
                            .jitter(0.5))
                    .block();
            return response;
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    public static String getCity(String iata) {
        if(cityMemory.containsKey(iata)) {
            return cityMemory.get(iata);
        } else {
            String url = UriComponentsBuilder.newInstance()
                    .host("test.api.amadeus.com/v1")
                    .scheme("https")
                    .path("/reference-data/locations")
                    .queryParam("subType", "AIRPORT")
                    .queryParam("keyword", iata.toUpperCase())
                    .queryParam("view", "LIGHT")
                    .build().toUriString();
            try {
                String response = webClient().get()
                        .uri(url)
                        .retrieve()
                        .bodyToMono(String.class)
                        .retryWhen(Retry.backoff(5, Duration.ofSeconds(1))
                                .maxBackoff(Duration.ofSeconds(8))
                                .jitter(0.5))
                        .block();
                JSONObject json = new JSONObject(response);
                String city;
                try {
                    city = json.optJSONArray("data").optJSONObject(0).optJSONObject("address").optString("cityName");
                }catch (Exception e) {
                    city = "";
                }
                cityMemory.put(iata, city);
                return city;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return "";
            }
        }
    }

    public static String getAirline(String keyword) {
        return carriers.optString(keyword, "");
    }

    public static String getAircraft(String keyword) {
        return aircrafts.optString(keyword, "");
    }

    public String getFlightOffers(FlightDTO flightDTO) {
        String response = "", returnDate = flightDTO.getReturnDate();
        boolean nonStop = flightDTO.isNonStop();
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance()
                .host(this.baseURL)
                .scheme("https")
                .path("/shopping/flight-offers")
                .queryParam("originLocationCode", flightDTO.getDepartureAirport().toUpperCase())
                .queryParam("destinationLocationCode", flightDTO.getArrivalAirport().toUpperCase())
                .queryParam("departureDate", flightDTO.getDepartureDate())
                .queryParam("adults", String.valueOf(flightDTO.getAdults()))
                .queryParam("currencyCode", flightDTO.getCurrency());
        if(!returnDate.isBlank()) {
            builder.queryParam("returnDate", returnDate);
        }
        if(nonStop) {
            builder.queryParam("nonStop",nonStop);
        }
        verifyToken();
        try {
            response = webClient().get()
                    .uri(builder.build().toUriString())
                    .httpRequest(httpRequest -> {
                        HttpClientRequest reactorRequest = httpRequest.getNativeRequest();
                        reactorRequest.responseTimeout(Duration.ofSeconds(10));
                    })
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            parseDictionary(new JSONObject(response));
            //System.out.println(response);
            return response;
        }catch (Exception e) {
            System.out.println(e.getMessage() + response);
            return response;
        }

    }

    private void parseDictionary(JSONObject jsonObject) {
        aircrafts = jsonObject.getJSONObject("dictionaries").getJSONObject("aircraft");
        carriers = jsonObject.getJSONObject("dictionaries").getJSONObject("carriers");
    }

    private void verifyToken() {
        if (Duration.between(lastTokenRefresh, Instant.now()).getSeconds() > 1799) {getAuthToken();}
    }
}
