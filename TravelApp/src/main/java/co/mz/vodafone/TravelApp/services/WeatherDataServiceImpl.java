package co.mz.vodafone.TravelApp.services;

import co.mz.vodafone.TravelApp.dtos.WeatherData;
import co.mz.vodafone.TravelApp.exceptions.InternalServerErrorException;
import co.mz.vodafone.TravelApp.exceptions.NoSuchElementException;
import co.mz.vodafone.TravelApp.exceptions.NotFoundException;
import co.mz.vodafone.TravelApp.feignclients.WeatherClient;
import co.mz.vodafone.TravelApp.interfaces.IGeoCodingService;
import co.mz.vodafone.TravelApp.interfaces.IWeatheDataService;
import feign.FeignException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WeatherDataServiceImpl implements IWeatheDataService {

    public static final String INTERNAL_API = " internal api ";
    public static final String CITY_IS_EMPTY = "City is empty";
    public static final String IS_NOT_FOUND = "is not found";
    private final WeatherClient _client;
    private final IGeoCodingService _geoCodingService;

    public WeatherDataServiceImpl(IGeoCodingService geoCodingService, WeatherClient client) {
        _geoCodingService = geoCodingService;
        _client = client;
    }

    @Override
    public WeatherData getWeatherDataByCityLanLng(double lat, double lng) {
        try {
            return _client.getWeatherData(lat, lng);
        } catch (FeignException ex) {
            throw new InternalServerErrorException(ex.status() + INTERNAL_API);
        } catch (Exception ex) {
            throw new InternalServerErrorException(ex.getLocalizedMessage());
        }
    }

    @Override
    public Optional<WeatherData> getWeatherDataByCityName(Optional<String> cityName) {
        if (cityName.isEmpty() || cityName.get().isEmpty()) {
            throw new NoSuchElementException(CITY_IS_EMPTY);
        }
        var cityOptional = _geoCodingService.getCityDetails(cityName.get());
        if (cityOptional.isEmpty()) {
            throw new NotFoundException(cityName.get().concat(IS_NOT_FOUND));
        }
        var city = cityOptional.get();
        var weatherOptional = getWeatherDataByCityLanLng(city.getLat(), city.getLon());
        return Optional.of(weatherOptional);
    }
}
