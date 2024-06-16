package co.mz.vodafone.TravelApp.interfaces;

import co.mz.vodafone.TravelApp.dtos.City;

import java.util.Optional;

public interface IGeoCodingService {
    public Optional<City> getCityDetails(String string);
}
