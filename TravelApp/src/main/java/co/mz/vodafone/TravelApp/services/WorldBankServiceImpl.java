package co.mz.vodafone.TravelApp.services;

import co.mz.vodafone.TravelApp.dtos.WorldBankData;
import co.mz.vodafone.TravelApp.dtos.WorldBankMetadataResponse;
import co.mz.vodafone.TravelApp.dtos.WorldBankResponse;
import co.mz.vodafone.TravelApp.exceptions.InternalServerErrorException;
import co.mz.vodafone.TravelApp.exceptions.NoSuchElementException;
import co.mz.vodafone.TravelApp.feignclients.WorldBankClient;
import co.mz.vodafone.TravelApp.interfaces.IWorldBankService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class WorldBankServiceImpl implements IWorldBankService {

    private static final String INTERNAL_API = "";
    public static final int YEAR_INTERVAL_STATS = 10;
    private final WorldBankClient _worldBankclient;

    public WorldBankServiceImpl(WorldBankClient worldBankclient) {
        _worldBankclient = worldBankclient;
    }

    @Override
    public Optional<WorldBankResponse> getWorldBankGpdIndicatorByCountry(Optional<String> country) {
        if (country.isEmpty() || country.get().isEmpty()) {
            throw new NoSuchElementException("No country selected");
        }

        int year = LocalDateTime.now().getYear();
        String yearInterval = (year - YEAR_INTERVAL_STATS) + ":" + year;

        try {
            Optional<ArrayList> result = Optional.ofNullable(_worldBankclient.getWorldBankIndicatorGpdByCountry(country.get(), yearInterval));

            if (result.isPresent()) {
                var worldBankArray = result.get();

                ObjectMapper objectMapper = getMapper();
                WorldBankMetadataResponse metadata = objectMapper.convertValue(worldBankArray.get(0), WorldBankMetadataResponse.class);

                List<WorldBankData> data = objectMapper.convertValue(worldBankArray.get(1), new TypeReference<List<WorldBankData>>() {});

                WorldBankResponse worldbankResponse = new WorldBankResponse();
                worldbankResponse.setMetadata(metadata);
                worldbankResponse.setData(data);

                return Optional.of(worldbankResponse);
            }

            return Optional.empty();

        } catch (FeignException ex) {
            throw new InternalServerErrorException("Feign exception: " + ex.status() + " " + ex.getMessage());
        } catch (Exception ex) {
            throw new InternalServerErrorException("Exception: " + ex.getLocalizedMessage());
        }
    }

    @Override
    public Optional<WorldBankResponse> getWorldBankPopulationIndicatorByCountry(Optional<String> country) {
        if (country.isEmpty() || country.get().isEmpty()) {
            throw new NoSuchElementException("No country selected");
        }

        int year = LocalDateTime.now().getYear();
        String yearInterval = (year - YEAR_INTERVAL_STATS) + ":" + year;

        try {
            Optional<ArrayList> result = Optional.ofNullable(_worldBankclient.getWorldBankIndicatorPopulationByCountry(country.get(), yearInterval));

            if (result.isPresent()) {
                var worldBankArray = result.get();

                ObjectMapper objectMapper = getMapper();
                WorldBankMetadataResponse metadata = objectMapper.convertValue(worldBankArray.get(0), WorldBankMetadataResponse.class);

                List<WorldBankData> data = objectMapper.convertValue(worldBankArray.get(1), new TypeReference<List<WorldBankData>>() {});

                WorldBankResponse worldbankResponse = new WorldBankResponse();
                worldbankResponse.setMetadata(metadata);
                worldbankResponse.setData(data);

                return Optional.of(worldbankResponse);
            }

            return Optional.empty();

        } catch (FeignException ex) {
            throw new InternalServerErrorException("Feign exception: " + ex.status() + " " + ex.getMessage());
        } catch (Exception ex) {
            throw new InternalServerErrorException("Exception: " + ex.getLocalizedMessage());
        }
    }
    private ObjectMapper getMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return  objectMapper;
    }
}



