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

    @GetMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ExchangeRateResponse getExchangeRate(@RequestParam(value = "symbol", required = false) String symbol,
                                                @RequestParam(value = "base",required = false) String base,
                                                @RequestParam(value = "start_date",required = false) Date start_date,
                                                               @RequestParam(value = "end_date", required = false) Date end_date
    );
}
