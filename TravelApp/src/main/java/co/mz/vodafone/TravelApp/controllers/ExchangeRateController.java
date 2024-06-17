package co.mz.vodafone.TravelApp.controllers;

import co.mz.vodafone.TravelApp.dtos.ErrorResponse;
import co.mz.vodafone.TravelApp.dtos.ExchangeRateResponse;
import co.mz.vodafone.TravelApp.dtos.WeatherData;
import co.mz.vodafone.TravelApp.interfaces.IExchangeRateService;
import co.mz.vodafone.TravelApp.interfaces.IWeatheDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "Exchange rate", description = "Get exchange rate of a specific country currency")
@CrossOrigin
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
    @Operation(
            summary = GET_EXCHANGE_RATE_DATA_FOR_A_GIVEN_SYMBOL,
            tags = {"Exchange rate"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = ExchangeRateResponse.class), mediaType = "application/json")}),
    })
    @GetMapping("/{symbol}")
    @CacheEvict(value = EXCHANGE_RATE, key = EXCHANGE_RATE_KEY)
    public ResponseEntity<ExchangeRateResponse> getExchangeRateFor(@Parameter(
            description = "symbol",
            required = true) @PathVariable("symbol") Optional<String> symbol) {
        Optional<ExchangeRateResponse> exchangeRateResponseOptional = _exchangeRateService.getExchangeRateBySymbol(symbol);
        return ResponseEntity.status(HttpStatus.OK).body(exchangeRateResponseOptional.get());
    }

    // Defaults endpoints if no symbol is provided
    @Operation(
            summary = "Defaults endpoints if no symbol is provided",
            tags = {"Exchange rate"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")}),
    })
    @GetMapping("")
    public ResponseEntity<ExchangeRateResponse> getDefaultExchangeRate() {
        return getExchangeRateFor(Optional.empty());
    }

    @Operation(
            summary = "Defaults endpoints if no symbol is provided",
            tags = {"Exchange rate"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")}),
    })
    @GetMapping("/")
    public ResponseEntity<ExchangeRateResponse> getDefaultExchangeRateEmpty() {
        return getExchangeRateFor(Optional.empty());
    }
}
