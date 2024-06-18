package co.mz.vodafone.TravelApp.services;

import co.mz.vodafone.TravelApp.dtos.WorldBankData;
import co.mz.vodafone.TravelApp.dtos.WorldBankMetadataResponse;
import co.mz.vodafone.TravelApp.dtos.WorldBankResponse;
import co.mz.vodafone.TravelApp.exceptions.BadRequestException;
import co.mz.vodafone.TravelApp.exceptions.InternalServerErrorException;
import co.mz.vodafone.TravelApp.exceptions.NoSuchElementException;
import co.mz.vodafone.TravelApp.exceptions.NotFoundException;
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

    public static final String NO_COUNTRY_SELECTED = "No country selected";
    private static final String INTERNAL_API = "";
    public static final int YEAR_INTERVAL_STATS = 10;
    public static final String FEIGN_EXCEPTION = "Feign exception: ";
    public static final String INVALID_INSERTED_KEY = "invalid inserted key ";
    public static final String FEIGN_EXCEPTION1 = "Feign exception: ";
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
        WorldBankResponse worldbankResponse = new WorldBankResponse();

        try {
            Optional<ArrayList> result = Optional.ofNullable(_worldBankclient.getWorldBankIndicatorGpdByCountry(country.get(), yearInterval));

            if (result.isPresent()) {

                var worldBankArray = result.get();

                if (containsErrorMessage(worldBankArray)) {
                    handleErrorMessage(worldBankArray, country.get());
                }
                if (worldBankArray.isEmpty()) {
                    return Optional.of(worldbankResponse);
                }

                ObjectMapper objectMapper = getMapper();
                WorldBankMetadataResponse metadata = objectMapper.convertValue(worldBankArray.get(0), WorldBankMetadataResponse.class);

                List<WorldBankData> data = objectMapper.convertValue(worldBankArray.get(1), new TypeReference<List<WorldBankData>>() {
                });

                worldbankResponse.setMetadata(metadata);
                worldbankResponse.setData(data);

                return Optional.of(worldbankResponse);
            }

            return Optional.empty();

        } catch (FeignException ex) {
            throw new InternalServerErrorException(FEIGN_EXCEPTION1 + ex.status() + " " + ex.getMessage());
        } catch (Exception ex) {
            throw new InternalServerErrorException(ex.getLocalizedMessage());
        }
    }

    @Override
    public Optional<WorldBankResponse> getWorldBankPopulationIndicatorByCountry(Optional<String> country) {
        if (country.isEmpty() || country.get().isEmpty()) {
            throw new NoSuchElementException(NO_COUNTRY_SELECTED);
        }

        int year = LocalDateTime.now().getYear();
        String yearInterval = (year - YEAR_INTERVAL_STATS) + ":" + year;
        WorldBankResponse worldbankResponse = new WorldBankResponse();

        try {
            Optional<ArrayList> result = Optional.ofNullable(_worldBankclient.getWorldBankIndicatorPopulationByCountry(country.get(), yearInterval));

            if (result.isPresent()) {
                var worldBankArray = result.get();

                if (containsErrorMessage(worldBankArray)) {
                    handleErrorMessage(worldBankArray, country.get());
                }
                if (worldBankArray.isEmpty()) {
                    return Optional.of(worldbankResponse);
                }
                ObjectMapper objectMapper = getMapper();
                WorldBankMetadataResponse metadata = objectMapper.convertValue(worldBankArray.get(0), WorldBankMetadataResponse.class);

                List<WorldBankData> data = objectMapper.convertValue(worldBankArray.get(1), new TypeReference<List<WorldBankData>>() {
                });

                worldbankResponse.setMetadata(metadata);
                worldbankResponse.setData(data);

                return Optional.of(worldbankResponse);
            }

            return Optional.empty();

        } catch (FeignException ex) {
            throw new InternalServerErrorException(FEIGN_EXCEPTION + ex.status() + " " + ex.getMessage());
        } catch (NotFoundException ex) {
            throw ex;
        }
    }

    private ObjectMapper getMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    private boolean containsErrorMessage(ArrayList<?> response) {
        if (response.size() > 0 && response.get(0) instanceof Map) {
            Map<?, ?> firstElement = (Map<?, ?>) response.get(0);
            return firstElement.containsKey("message");
        }
        return false;
    }

    private void handleErrorMessage(ArrayList<?> response, String key) {
        Map<?, ?> errorMessage = (Map<?, ?>) response.get(0);
        List<?> messageList = (List<?>) errorMessage.get("message");
        if (!messageList.isEmpty() && messageList.get(0) instanceof Map) {
            throw new BadRequestException(INVALID_INSERTED_KEY.concat(key));
        }
    }
}



