package org.adaschool.Weather.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.adaschool.Weather.data.WeatherApiResponse;
import org.adaschool.Weather.data.WeatherReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

public class WeatherReportServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WeatherReportService weatherReportService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetWeatherReport() {
        double latitude = 37.8267;
        double longitude = -122.4233;
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=your-api-key";

        WeatherApiResponse mockResponse = new WeatherApiResponse();
        WeatherApiResponse.Main mockMain = new WeatherApiResponse.Main();
        mockMain.setTemperature(20.0);
        mockMain.setHumidity(65.0);
        mockResponse.setMain(mockMain);
        when(restTemplate.getForObject(apiUrl, WeatherApiResponse.class)).thenReturn(mockResponse);
        WeatherReport result = weatherReportService.getWeatherReport(latitude, longitude);
        assertNotNull(result);
        assertEquals(20.0, result.getTemperature());
        assertEquals(65.0, result.getHumidity());
    }

    @Test
    public void testGetWeatherReport_whenApiReturnsNull() {
        double latitude = 37.8267;
        double longitude = -122.4233;
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=your-api-key";
        when(restTemplate.getForObject(apiUrl, WeatherApiResponse.class)).thenReturn(null);
        WeatherReport result = weatherReportService.getWeatherReport(latitude, longitude);
        assertNotNull(result);
        assertNull(result.getTemperature());
        assertNull(result.getHumidity());
    }
}

