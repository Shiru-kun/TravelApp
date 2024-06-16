package co.mz.vodafone.TravelApp.feignclients;

import co.mz.vodafone.TravelApp.configuration.FeignClientConfig;
import co.mz.vodafone.TravelApp.dtos.City;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "GeoCodingClient", url = "${travel-app.external-endpoints.geocoding}", configuration = FeignClientConfig.class)
public interface GeoCodingClient {
    @GetMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<City> getCityDetailsByName(@RequestParam("q") String city );
}
