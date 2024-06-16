package co.mz.vodafone.TravelApp.controllers;

import co.mz.vodafone.TravelApp.dtos.City;
import co.mz.vodafone.TravelApp.interfaces.IGeoCodingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/1.0/geocoding")
public class GeoCodingController {

    private final IGeoCodingService geoCodingService;

    public GeoCodingController(IGeoCodingService geoCodingService) {
        this.geoCodingService = geoCodingService;
    }

    @GetMapping("/")
    public ResponseEntity<City> getCityDetails() {
       return getCityDetails(Optional.empty());
    }

    @GetMapping("/{city}")
    public ResponseEntity<City> getCityDetails(@PathVariable("city") Optional<String> cityName) {
        // Check if the city name is provided
        if (cityName.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Fetch city details
        Optional<City> cityOptional = geoCodingService.getCityDetails(cityName.get());

        // Check if city details were found
        if (cityOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Return city details
        return new ResponseEntity<>(cityOptional.get(), HttpStatus.OK);
    }
}
