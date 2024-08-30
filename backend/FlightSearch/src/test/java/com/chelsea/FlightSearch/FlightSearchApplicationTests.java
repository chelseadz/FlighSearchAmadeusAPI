package com.chelsea.FlightSearch;

import com.chelsea.FlightSearch.Services.AmadeusService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FlightSearchApplicationTests {

    @Autowired
    private AmadeusService amadeusService;

    @Test
    @DisplayName("GetAuthToken Should return success")
    public void testGetAuthTokenReturnsSuccess() {
        String result = amadeusService.getAuthToken();

        assert(result.equals("success"));
    }
}
