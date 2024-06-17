package co.mz.vodafone.TravelApp.controllers;

import co.mz.vodafone.TravelApp.dtos.WeatherData;
import co.mz.vodafone.TravelApp.interfaces.IWeatheDataService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "Weather", description = "Weather for a specific city")
@CrossOrigin
@RestController
@RequestMapping("/1.0/weather")
public class WeatherController {

    public static final String WEATHER_DATA = "weatherData";
    public static final String WEATHER_DATA_KEY = "#weatherDataKey";
    public static final String GET_WEATHER_DATA_FOR_A_GIVEN_CITY = "Get weather data for a given city";
    private final IWeatheDataService weatherDataService;

    public WeatherController(IWeatheDataService weatherDataService) {
        this.weatherDataService = weatherDataService;
    }

    // Get weather data for a given city
    @Operation(
            summary = GET_WEATHER_DATA_FOR_A_GIVEN_CITY,
            tags = {"Weather"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = WeatherData.class), mediaType = "application/json")}),
    })
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
