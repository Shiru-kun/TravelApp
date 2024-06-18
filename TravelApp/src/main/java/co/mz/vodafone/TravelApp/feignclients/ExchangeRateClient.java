package co.mz.vodafone.TravelApp.feignclients;

import co.mz.vodafone.TravelApp.configuration.FeignClientConfig;
import co.mz.vodafone.TravelApp.dtos.City;
import co.mz.vodafone.TravelApp.dtos.ExchangeRateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@FeignClient(name = "ExchangeRateClient", url = "${travel-app.external-endpoints.exchange-rate}", configuration = FeignClientConfig.class)
public interface ExchangeRateClient {
    @GetMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ExchangeRateResponse getExchangeRateByCountrySymbol(@RequestParam(value = "symbols", required = false) String symbol
    );

}
