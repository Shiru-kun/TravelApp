package co.mz.vodafone.TravelApp.interfaces;

import co.mz.vodafone.TravelApp.dtos.WorldBankResponse;

import java.util.Optional;

public interface IWorldBankService {
    public Optional<WorldBankResponse> getWorldBankGpdIndicatorByCountry(Optional<String> country);
    public Optional<WorldBankResponse> getWorldBankPopulationIndicatorByCountry(Optional<String> country);
}
