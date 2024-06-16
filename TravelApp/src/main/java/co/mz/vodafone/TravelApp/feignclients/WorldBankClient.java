package co.mz.vodafone.TravelApp.feignclients;

import co.mz.vodafone.TravelApp.configuration.FeignClientConfig;
import co.mz.vodafone.TravelApp.dtos.WorldBankResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "WorldBankClient", url = "${travel-app.external-endpoints.world-bank-api}", configuration = FeignClientConfig.class)
public interface WorldBankClient {
    @GetMapping(value = "/{country}/indicators/NY.GDP.MKTP.CD?format=json&date={year-interval}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList getWorldBankIndicatorGpdByCountry(@PathVariable("country") String country,@PathVariable("year-interval") String yearInterval );

    @GetMapping(value = "/{country}/indicators/SP.POP.TOTL?format=json&date={year-interval}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList getWorldBankIndicatorPopulationByCountry(@PathVariable("country") String country, @PathVariable("year-interval") String yearInterval);
}
