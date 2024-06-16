package co.mz.vodafone.TravelApp.services;

import co.mz.vodafone.TravelApp.dtos.ExchangeRateResponse;
import co.mz.vodafone.TravelApp.exceptions.BadRequestException;
import co.mz.vodafone.TravelApp.exceptions.InternalServerErrorException;
import co.mz.vodafone.TravelApp.exceptions.NoSuchElementException;
import co.mz.vodafone.TravelApp.feignclients.ExchangeRateClient;
import co.mz.vodafone.TravelApp.interfaces.IExchangeRateService;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExchangeRateServiceImpl implements IExchangeRateService {
    public static final String INTERNAL_SERVER_API = " Internal server api";
    public static final String NO_SYMBOL_INSERTED = " No symbol inserted";
    public static final String NO_VALID_INPUT_FOR = "No valid input for";
    private final ExchangeRateClient _exchangeRateClient;

    public ExchangeRateServiceImpl(ExchangeRateClient exchangeRateClient) {
        _exchangeRateClient = exchangeRateClient;
    }

    @Override
    public Optional<ExchangeRateResponse> getExchangeRateBySymbol(Optional<String> symbol) {
        if (symbol.isEmpty() || symbol.get().isEmpty()) {
            throw new NoSuchElementException(NO_SYMBOL_INSERTED);
        }
        try {
            return Optional.of(_exchangeRateClient.getExchangeRateByCountrySymbol(symbol.get()));
        } catch (FeignException ex) {
            if (ex.status() == HttpStatus.BAD_REQUEST.value()) {
                throw new BadRequestException(NO_VALID_INPUT_FOR + symbol.get());
            }
            throw new InternalServerErrorException(String.valueOf(ex.status()).concat(INTERNAL_SERVER_API));
        } catch (Exception ex) {
            throw new InternalServerErrorException(INTERNAL_SERVER_API);
        }
    }
}
