package co.mz.vodafone.TravelApp.services;

import co.mz.vodafone.TravelApp.dtos.City;
import co.mz.vodafone.TravelApp.exceptions.InternalServerErrorException;
import co.mz.vodafone.TravelApp.exceptions.NoSuchElementException;
import co.mz.vodafone.TravelApp.exceptions.NotFoundException;
import co.mz.vodafone.TravelApp.feignclients.GeoCodingClient;
import co.mz.vodafone.TravelApp.interfaces.IGeoCodingService;
import feign.FeignException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GeoCodingServiceImpl implements IGeoCodingService {

    public static final String CITY_IS_EMPTY = "City is empty";
    public static final String INTERNAL_API_ERROR = " internal API error";
    public static final String IS_NOT_FOUND = " is not found";
    private final GeoCodingClient _client;

    public GeoCodingServiceImpl(GeoCodingClient client) {
        _client = client;
    }

    @Override
    public Optional<City> getCityDetails(String cityString) {
        if (cityString == null || cityString.isBlank()) {
            throw new NoSuchElementException(CITY_IS_EMPTY);
        }

        List<City> cities;
        try {
            cities = _client.getCityDetailsByName(cityString);
        } catch (FeignException ex) {
            throw new InternalServerErrorException(ex.status() + INTERNAL_API_ERROR);
        }

        if (cities == null || cities.isEmpty()) {
            throw new NotFoundException(cityString.concat(IS_NOT_FOUND));
        }

        return cities.stream().findFirst();
    }


}
