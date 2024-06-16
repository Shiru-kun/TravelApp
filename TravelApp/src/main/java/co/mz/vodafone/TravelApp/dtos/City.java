package co.mz.vodafone.TravelApp.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@NoArgsConstructor
@Data
public class City {
    private String name;
   // private Map<String, String> local_names;
    private double lat;
    private double lon;
    private String country;
    private String state;
}
