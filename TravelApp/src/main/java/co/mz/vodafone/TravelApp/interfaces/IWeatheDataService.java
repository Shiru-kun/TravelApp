package co.mz.vodafone.TravelApp.interfaces;

import co.mz.vodafone.TravelApp.dtos.WeatherData;

import java.util.Optional;

public interface IWeatheDataService {
    public WeatherData getWeatherDataByCityLanLng(double lat, double lng);
    public Optional<WeatherData> getWeatherDataByCityName(Optional<String> cityName);
}
