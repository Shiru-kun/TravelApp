package co.mz.vodafone.TravelApp.services;

import co.mz.vodafone.TravelApp.dtos.City;
import co.mz.vodafone.TravelApp.exceptions.InternalServerErrorException;
import co.mz.vodafone.TravelApp.exceptions.NoSuchElementException;
import co.mz.vodafone.TravelApp.exceptions.NotFoundException;
import co.mz.vodafone.TravelApp.feignclients.GeoCodingClient;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class GeoCodingServiceImplTest {

    @Mock
    private GeoCodingClient geoCodingClient;

    @InjectMocks
    private GeoCodingServiceImpl geoCodingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCityDetails_CityIsNull() {
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            geoCodingService.getCityDetails(null);
        });

        String expectedMessage = "City is empty";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetCityDetails_CityIsBlank() {
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            geoCodingService.getCityDetails("");
        });

        String expectedMessage = "City is empty";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetCityDetails_CityNotFound() {
        String cityName = "InvalidCity";
        when(geoCodingClient.getCityDetailsByName(cityName)).thenReturn(new ArrayList<>());

        Exception exception = assertThrows(NotFoundException.class, () -> {
            geoCodingService.getCityDetails(cityName);
        });

        String expectedMessage = cityName.concat(" is not found");
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetCityDetails_ValidCity() {
        String cityName = "Maputo";
        City city = new City();
        city.setName(cityName);
        List<City> cities = new ArrayList<>();
        cities.add(city);

        when(geoCodingClient.getCityDetailsByName(cityName)).thenReturn(cities);

        Optional<City> cityOptional = geoCodingService.getCityDetails(cityName);

        assertTrue(cityOptional.isPresent());
        assertEquals(cityName, cityOptional.get().getName());
    }
}
