package co.mz.vodafone.TravelApp.services;

import co.mz.vodafone.TravelApp.dtos.City;
import co.mz.vodafone.TravelApp.dtos.WeatherData;
import co.mz.vodafone.TravelApp.exceptions.InternalServerErrorException;
import co.mz.vodafone.TravelApp.exceptions.NoSuchElementException;
import co.mz.vodafone.TravelApp.exceptions.NotFoundException;
import co.mz.vodafone.TravelApp.feignclients.WeatherClient;
import co.mz.vodafone.TravelApp.interfaces.IGeoCodingService;
import feign.FeignException;
import feign.Request;
import feign.RequestTemplate;
import feign.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WeatherDataServiceImplTest {

    @Mock
    private WeatherClient weatherClient;

    @Mock
    private IGeoCodingService geoCodingService;

    @InjectMocks
    private WeatherDataServiceImpl weatherDataService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetWeatherDataByCityLanLng_ValidCoordinates() {
        double lat = -25.9662;
        double lon = 32.5675;
        String city = "Maputo";
        WeatherData weatherData = new WeatherData().toBuilder().name(city).build();

        when(weatherClient.getWeatherData(lat, lon)).thenReturn(weatherData);

        WeatherData result = weatherDataService.getWeatherDataByCityLanLng(lat, lon);

        assertNotNull(result);
        assertEquals(city, result.getName());
    }

    @Test
    void testGetWeatherDataByCityLanLng_InternalServerError() {
        double lat = -25.9662;
        double lon = 32.5675;
        Map<String, Collection<String>> headers = Collections.emptyMap();
        Request request = Request.create(Request.HttpMethod.GET, "/api/weather", headers, null, StandardCharsets.UTF_8);
        FeignException feignException = FeignException.errorStatus("GET", Response.builder()
                .status(500)
                .reason("Internal Server Error")
                .request(request)
                .build());

        when(weatherClient.getWeatherData(lat, lon)).thenThrow(feignException);

        Exception exception = assertThrows(InternalServerErrorException.class, () -> {
            weatherDataService.getWeatherDataByCityLanLng(lat, lon);
        });

        String expectedMessage = "500 internal api ";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetWeatherDataByCityName_CityNameIsEmpty() {
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            weatherDataService.getWeatherDataByCityName(Optional.of(""));
        });

        String expectedMessage = "City is empty";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetWeatherDataByCityName_InternalServerError() {
        String cityName = "Maputo";
        City city = new City();
        city.setName(cityName);
        city.setLat(-25.9662);
        city.setLon(32.5675);

        when(geoCodingService.getCityDetails(cityName)).thenReturn(Optional.of(city));

        Map<String, Collection<String>> headers = Collections.emptyMap();
        Request request = Request.create(Request.HttpMethod.GET, "/api/weather", headers, null, StandardCharsets.UTF_8);
        FeignException feignException = FeignException.errorStatus("GET", Response.builder()
                .status(500)
                .reason("Internal Server Error")
                .request(request)
                .build());

        when(weatherClient.getWeatherData(city.getLat(), city.getLon())).thenThrow(feignException);

        Exception exception = assertThrows(InternalServerErrorException.class, () -> {
            weatherDataService.getWeatherDataByCityName(Optional.of(cityName));
        });

        String expectedMessage = "500 internal api ";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    void testGetWeatherDataByCityName_InternalServerError401() {
        String cityName = "Maputo";
        City city = new City();
        city.setName(cityName);
        city.setLat(-25.9662);
        city.setLon(32.5675);

        when(geoCodingService.getCityDetails(cityName)).thenReturn(Optional.of(city));

        Map<String, Collection<String>> headers = Collections.emptyMap();
        Request request = Request.create(Request.HttpMethod.GET, "/api/weather", headers, null, StandardCharsets.UTF_8);
        FeignException feignException = FeignException.errorStatus("GET", Response.builder()
                .status(401)
                .reason("Internal Server Error")
                .request(request)
                .build());

        when(weatherClient.getWeatherData(city.getLat(), city.getLon())).thenThrow(feignException);

        Exception exception = assertThrows(InternalServerErrorException.class, () -> {
            weatherDataService.getWeatherDataByCityName(Optional.of(cityName));
        });

        String expectedMessage = "401 internal api ";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetWeatherDataByCityName_ValidCity() {
        String cityName = "Maputo";
        City city = new City();
        city.setName(cityName);
        city.setLat(-25.9662);
        city.setLon(32.5675);

        WeatherData weatherData = new WeatherData().toBuilder().name(cityName).build();

        when(geoCodingService.getCityDetails(cityName)).thenReturn(Optional.of(city));
        when(weatherClient.getWeatherData(city.getLat(), city.getLon())).thenReturn(weatherData);

        Optional<WeatherData> result = weatherDataService.getWeatherDataByCityName(Optional.of(cityName));

        assertTrue(result.isPresent());
        assertEquals(weatherData.getName(), result.get().getName());
    }
}
