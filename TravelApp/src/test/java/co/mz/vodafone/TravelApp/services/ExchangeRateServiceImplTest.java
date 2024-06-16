package co.mz.vodafone.TravelApp.services;

import co.mz.vodafone.TravelApp.dtos.ExchangeRateResponse;
import co.mz.vodafone.TravelApp.exceptions.InternalServerErrorException;
import co.mz.vodafone.TravelApp.exceptions.NoSuchElementException;
import co.mz.vodafone.TravelApp.feignclients.ExchangeRateClient;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ExchangeRateServiceImplTest {

    @Mock
    private ExchangeRateClient exchangeRateClient;

    @InjectMocks
    private ExchangeRateServiceImpl exchangeRateService;

    private static final String VALID_SYMBOL = "MZN";
    private static final String INVALID_SYMBOL = "";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetExchangeRateBySymbolThrowsNoSuchElementExceptionWhenSymbolIsEmpty() {
        assertThrows(NoSuchElementException.class, () -> {
            exchangeRateService.getExchangeRateBySymbol(Optional.of(INVALID_SYMBOL));
        });

        assertThrows(NoSuchElementException.class, () -> {
            exchangeRateService.getExchangeRateBySymbol(Optional.empty());
        });
    }

    @Test
    void testGetExchangeRateBySymbolReturnsValidResponseWhenSymbolIsValid() {
        ExchangeRateResponse response = new ExchangeRateResponse();
        response.setSuccess(true);
        response.setBase("EUR");
        response.setDate("2024-06-15");
        response.setRates(Map.of(VALID_SYMBOL, 68.280013));

        when(exchangeRateClient.getExchangeRateByCountrySymbol(anyString())).thenReturn(response);

        Optional<ExchangeRateResponse> result = exchangeRateService.getExchangeRateBySymbol(Optional.of(VALID_SYMBOL));

        assertTrue(result.isPresent());
        assertEquals(response, result.get());
    }

    @Test
    void testGetExchangeRateBySymbolThrowsInternalServerErrorExceptionOnFeignException() {
        when(exchangeRateClient.getExchangeRateByCountrySymbol(anyString())).thenThrow(FeignException.class);

        assertThrows(InternalServerErrorException.class, () -> {
            exchangeRateService.getExchangeRateBySymbol(Optional.of(VALID_SYMBOL));
        });
    }

    @Test
    void testGetExchangeRateBySymbolThrowsInternalServerErrorExceptionOnOtherExceptions() {
        when(exchangeRateClient.getExchangeRateByCountrySymbol(anyString())).thenThrow(RuntimeException.class);

        assertThrows(InternalServerErrorException.class, () -> {
            exchangeRateService.getExchangeRateBySymbol(Optional.of(VALID_SYMBOL));
        });
    }
}
