package co.mz.vodafone.TravelApp.controllers;

import co.mz.vodafone.TravelApp.dtos.WorldBankResponse;
import co.mz.vodafone.TravelApp.interfaces.IWorldBankService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Tag(name = "World bank", description = "Get GPD and Population data")
@RestController
@RequestMapping("/1.0/world-bank")
public class WorldBankController {

    public static final String GPD_DATA = "gpdData";
    public static final String GPD_DATA_KEY = "gpdDatakey";
    public static final String POPULATION_DATA = "populationData";
    public static final String POPULATION_DATA_KEY = "populationDataKey";
    public static final String GET_WORLD_BANK_GPD_DATA_BY_COUNTRY_CODE = "get world bank gpd data by country code";
    public static final String GET_WORLD_BANK_POPULATION_DATA_BY_COUNTRY_CODE = "get world bank population data by country code";
    public static final String GET_WORLD_BANK_GPD_DATA_FOR_A_GIVEN_COUNTRY_10_YEARS_TO_NOW = "Get world bank gpd data for a given country 10 years to now";
    public static final String GET_WORLD_BANK_POPULATION_DATA_FOR_A_GIVEN_COUNTRY_10_YEARS_TO_NOW = "Get world bank population data for a given country 10 years to now";

    private final IWorldBankService _worldBankService;
    public WorldBankController(IWorldBankService worldBankService){
        _worldBankService = worldBankService;
    }

    @Operation(
            summary = GET_WORLD_BANK_GPD_DATA_FOR_A_GIVEN_COUNTRY_10_YEARS_TO_NOW,
            tags = { "World bank"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = WorldBankResponse.class), mediaType = "application/json") }),
    })
    @GetMapping("/gpd/{country}")
    @CacheEvict(value = GPD_DATA, key = GPD_DATA_KEY)
    public ResponseEntity<WorldBankResponse> getWorldBankGpdResponseByCountry(@Parameter(

            description = GET_WORLD_BANK_GPD_DATA_BY_COUNTRY_CODE,
            required = true) @PathVariable("country") Optional<String> country){
        return ResponseEntity.ok(_worldBankService.getWorldBankGpdIndicatorByCountry(country).get());
    }

    @Operation(
            summary = GET_WORLD_BANK_POPULATION_DATA_FOR_A_GIVEN_COUNTRY_10_YEARS_TO_NOW,
            tags = { "World bank"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = WorldBankResponse.class), mediaType = "application/json") }),
    })    @GetMapping("/population/{country}")
    @CacheEvict(value = POPULATION_DATA, key = POPULATION_DATA_KEY)
    public ResponseEntity<WorldBankResponse> getWorldBankPopulationResponseByCountry(@Parameter(
            description = GET_WORLD_BANK_POPULATION_DATA_BY_COUNTRY_CODE,
            required = true) @PathVariable("country") Optional<String> country){
        return ResponseEntity.ok(_worldBankService.getWorldBankPopulationIndicatorByCountry(country).get());
    }
}
