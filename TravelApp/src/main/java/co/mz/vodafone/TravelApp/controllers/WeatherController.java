package co.mz.vodafone.TravelApp.controllers;

import co.mz.vodafone.TravelApp.dtos.WeatherData;
import co.mz.vodafone.TravelApp.interfaces.IWeatheDataService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/1.0/weather")
public class WeatherController {

    public static final String WEATHER_DATA = "weatherData";
    public static final String WEATHER_DATA_KEY = "#weatherDataKey";
    private final IWeatheDataService weatherDataService;

    public WeatherController(IWeatheDataService weatherDataService) {
        this.weatherDataService = weatherDataService;
    }

    // Get weather data for a given city
    @Tag(name = "get", description = "Get weather data for a given city")
    @GetMapping("/{city}")
    @CacheEvict(value = WEATHER_DATA, key = WEATHER_DATA_KEY)
    public ResponseEntity<WeatherData> getWeatherFor(@Parameter(
            description = "City",
            required = true) @PathVariable("city") Optional<String> city) {
        Optional<WeatherData> weatherDataOptional = weatherDataService.getWeatherDataByCityName(city);
        return ResponseEntity.status(HttpStatus.OK).body(weatherDataOptional.get());
    }

    // Defaults endpoints if no city is provided
    @GetMapping("")
    public ResponseEntity<WeatherData> getDefaultWeather() {
        return getWeatherFor(Optional.empty());
    }
    @GetMapping("/")
    public ResponseEntity<WeatherData> getDefaultWeatherEmpty() {
        return getWeatherFor(Optional.empty());
    }
}
