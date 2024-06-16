package co.mz.vodafone.TravelApp.interfaces;

import co.mz.vodafone.TravelApp.dtos.ExchangeRateResponse;

import java.util.Optional;

public interface IExchangeRateService {
    public Optional<ExchangeRateResponse> getExchangeRateBySymbol(Optional<String> symbol);
}
