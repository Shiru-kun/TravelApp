package co.mz.vodafone.TravelApp.feignclients;

import co.mz.vodafone.TravelApp.configuration.FeignClientConfig;
import co.mz.vodafone.TravelApp.dtos.City;
import co.mz.vodafone.TravelApp.dtos.WeatherData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "WeatherClient", url = "${travel-app.external-endpoints.weather}", configuration = FeignClientConfig.class)
public interface WeatherClient {

    @GetMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    WeatherData getWeatherData(@RequestParam("lat") double lat, @RequestParam("lon") double lng);
}
