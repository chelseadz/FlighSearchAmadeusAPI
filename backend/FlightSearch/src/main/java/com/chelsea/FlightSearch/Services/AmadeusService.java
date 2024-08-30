package com.chelsea.FlightSearch.Services;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.json.JSONObject;

import static org.springframework.web.reactive.function.BodyInserters.fromFormData;


@Service
public class AmadeusService {

    @Value("${clientId}")
    private String clientId;

    @Value("${clientSecret}")
    private String clientSecret;

    @Value("${amadeus.url}")
    private String baseURL;

    private String accessToken;

    @Bean
    private WebClient webClient() {
        return WebClient.create();
    }

    @Bean
    public String getAuthToken() {
        String url = "https://test.api.amadeus.com/v1/security/oauth2/token";

        try {
            String response = webClient().post()
                    .uri(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromFormData("grant_type", "client_credentials")
                            .with("client_id", clientId)
                            .with("client_secret", clientSecret))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            JSONObject json = new JSONObject(response);
            accessToken = json.getString("access_token");
            return "success";
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return (e.getMessage());
        }
    }

    public String getFlightOffers() {
        String url = baseURL + "/shopping/flight-offers";

        String response = webClient().get()
                .uri(url)
                .headers(h -> h.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return response;
    }
}
