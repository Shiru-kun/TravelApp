package co.mz.vodafone.TravelApp.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
public class Sys {
    private String country;
    private long sunrise;
    private long sunset;
}
