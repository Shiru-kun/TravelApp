package co.mz.vodafone.TravelApp.services;


import co.mz.vodafone.TravelApp.dtos.WorldBankResponse;
import co.mz.vodafone.TravelApp.exceptions.InternalServerErrorException;
import co.mz.vodafone.TravelApp.exceptions.NoSuchElementException;
import co.mz.vodafone.TravelApp.feignclients.WorldBankClient;

import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WorldBankServiceImplTest {

    @Mock
    private WorldBankClient worldBankClient;

    @InjectMocks
    private WorldBankServiceImpl worldBankService;

    private static final String TEST_COUNTRY_CODE = "MZ";
    private static final String INVALID_TEST_COUNTRY_CODE = "MZNN";
    private static final int CURRENT_YEAR = LocalDateTime.now().getYear();
    private static final String TEST_YEAR_INTERVAL = (CURRENT_YEAR - 10) + ":" + CURRENT_YEAR;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetWorldBankGpdIndicatorByCountry_Success() {
        ArrayList mockResponse = createMockResponse();

        when(worldBankClient.getWorldBankIndicatorGpdByCountry(TEST_COUNTRY_CODE, TEST_YEAR_INTERVAL))
                .thenReturn(mockResponse);

        Optional<WorldBankResponse> result = worldBankService.getWorldBankGpdIndicatorByCountry(Optional.of(TEST_COUNTRY_CODE));

        assertTrue(result.isPresent());
        assertEquals(64, result.get().getMetadata().getTotal());
        assertEquals(1, result.get().getData().size());
    }

    @Test
    void testGetWorldBankGpdIndicatorByCountry_FeignException() {
        when(worldBankClient.getWorldBankIndicatorGpdByCountry(TEST_COUNTRY_CODE, TEST_YEAR_INTERVAL))
                .thenThrow(FeignException.class);

        assertThrows(InternalServerErrorException.class,
                () -> worldBankService.getWorldBankGpdIndicatorByCountry(Optional.of(TEST_COUNTRY_CODE)));
    }

    @Test
    void testGetWorldBankGpdIndicatorByCountry_OtherException() {
        when(worldBankClient.getWorldBankIndicatorGpdByCountry(TEST_COUNTRY_CODE, TEST_YEAR_INTERVAL))
                .thenThrow(RuntimeException.class);

        assertThrows(InternalServerErrorException.class,
                () -> worldBankService.getWorldBankGpdIndicatorByCountry(Optional.of(TEST_COUNTRY_CODE)));
    }

    @Test
    void testGetWorldBankGpdIndicatorByCountry_NoCountrySelected() {
        assertThrows(NoSuchElementException.class,
                () -> worldBankService.getWorldBankGpdIndicatorByCountry(Optional.empty()));
    }

    @Test
    void testGetWorldBankPopulationIndicatorByCountry_Success() {
        ArrayList<Object> mockResponse = createMockResponse();

        when(worldBankClient.getWorldBankIndicatorPopulationByCountry(TEST_COUNTRY_CODE, TEST_YEAR_INTERVAL))
                .thenReturn(mockResponse);

        Optional<WorldBankResponse> result = worldBankService.getWorldBankPopulationIndicatorByCountry(Optional.of(TEST_COUNTRY_CODE));

        assertTrue(result.isPresent());
        assertEquals(64, result.get().getMetadata().getTotal());
        assertEquals(1, result.get().getData().size());
    }

    @Test
    void testGetWorldBankPopulationIndicatorByCountry_FeignException() {
        when(worldBankClient.getWorldBankIndicatorPopulationByCountry(TEST_COUNTRY_CODE, TEST_YEAR_INTERVAL))
                .thenThrow(FeignException.class);

        assertThrows(InternalServerErrorException.class,
                () -> worldBankService.getWorldBankPopulationIndicatorByCountry(Optional.of(TEST_COUNTRY_CODE)));
    }

    @Test
    void testGetWorldBankPopulationIndicatorByCountry_NoCountrySelected() {
        assertThrows(NoSuchElementException.class,
                () -> worldBankService.getWorldBankPopulationIndicatorByCountry(Optional.empty()));
    }
    @Test
    void testGetWorldBankGpdIndicatorByCountry_ErrorResponse() {
        ArrayList<Object> mockErrorResponse = createMockErrorResponse();

        when(worldBankClient.getWorldBankIndicatorGpdByCountry(INVALID_TEST_COUNTRY_CODE, TEST_YEAR_INTERVAL))
                .thenReturn(mockErrorResponse);

    assertThrows(InternalServerErrorException.class, () ->
                worldBankService.getWorldBankGpdIndicatorByCountry(Optional.of(INVALID_TEST_COUNTRY_CODE))
        );

    }

    private ArrayList<Object> createMockResponse() {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("page", 1);
        metadata.put("pages", 2);
        metadata.put("per_page", 50);
        metadata.put("total", 64);
        metadata.put("sourceid", "2");
        metadata.put("lastupdated", "2024-05-30");

        List<Map<String, Object>> data = new ArrayList<>();
        Map<String, Object> dataEntry = new HashMap<>();
        Map<String, String> indicator = new HashMap<>();
        indicator.put("id", "NY.GDP.MKTP.CD");
        indicator.put("value", "GDP (current US$)");
        dataEntry.put("indicator", indicator);

        Map<String, String> country = new HashMap<>();
        country.put("id", "MZ");
        country.put("value", "Mozambique");
        dataEntry.put("country", country);

        dataEntry.put("countryiso3code", "MOZ");
        dataEntry.put("date", "2023");
        dataEntry.put("value", null);
        dataEntry.put("unit", "");
        dataEntry.put("obs_status", "");
        dataEntry.put("decimal", 0);
        data.add(dataEntry);

        ArrayList<Object> mockResponse = new ArrayList<>();
        mockResponse.add(metadata);
        mockResponse.add(data);

        return mockResponse;
    }
    private ArrayList<Object> createMockErrorResponse() {
        Map<String, Object> errorMessage = new HashMap<>();
        List<Map<String, Object>> messageList = new ArrayList<>();
        Map<String, Object> messageDetails = new HashMap<>();
        messageDetails.put("id", 120);
        messageDetails.put("key", "Invalid value");
        messageDetails.put("value", "The provided parameter value is not valid");
        messageList.add(messageDetails);
        errorMessage.put("message", messageList);

        ArrayList<Object> mockErrorResponse = new ArrayList<>();
        mockErrorResponse.add(errorMessage);

        return mockErrorResponse;
    }
}
