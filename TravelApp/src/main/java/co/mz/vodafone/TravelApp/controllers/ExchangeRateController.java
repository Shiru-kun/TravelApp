package co.mz.vodafone.TravelApp.controllers;

import co.mz.vodafone.TravelApp.dtos.ExchangeRateResponse;
import co.mz.vodafone.TravelApp.dtos.WeatherData;
import co.mz.vodafone.TravelApp.interfaces.IExchangeRateService;
import co.mz.vodafone.TravelApp.interfaces.IWeatheDataService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/1.0/exchange-rate")
public class ExchangeRateController {

    public static final String EXCHANGE_RATE = "exchangeRate";
    public static final String EXCHANGE_RATE_KEY = "#exchangeRateKey";
    public static final String GET_EXCHANGE_RATE_DATA_FOR_A_GIVEN_SYMBOL = "Get exchange rate data for a given symbol";
    private final IExchangeRateService _exchangeRateService;

    public ExchangeRateController(IExchangeRateService exchangeRateService) {
        _exchangeRateService = exchangeRateService;
    }

    // Get exchange rate data for a given symbol
    @Tag(name = "get", description = GET_EXCHANGE_RATE_DATA_FOR_A_GIVEN_SYMBOL)
    @GetMapping("/{symbol}")
    @CacheEvict(value = EXCHANGE_RATE, key = EXCHANGE_RATE_KEY)
    public ResponseEntity<ExchangeRateResponse> getExchangeRateFor(@Parameter(
            description = "symbol",
            required = true) @PathVariable("symbol") Optional<String> symbol) {
        Optional<ExchangeRateResponse> exchangeRateResponseOptional = _exchangeRateService.getExchangeRateBySymbol(symbol);
        return ResponseEntity.status(HttpStatus.OK).body(exchangeRateResponseOptional.get());
    }

    // Defaults endpoints if no symbol is provided
    @GetMapping("")
    public ResponseEntity<ExchangeRateResponse> getDefaultExchangeRate() {
        return getExchangeRateFor(Optional.empty());
    }
    @GetMapping("/")
    public ResponseEntity<ExchangeRateResponse> getDefaultExchangeRateEmpty() {
        return getExchangeRateFor(Optional.empty());
    }
}
