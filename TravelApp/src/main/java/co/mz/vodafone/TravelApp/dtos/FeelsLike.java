package co.mz.vodafone.TravelApp.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
public class FeelsLike {
    private double day;
    private double night;
    private double eve;
    private double morn;
}
