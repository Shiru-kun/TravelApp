package co.mz.vodafone.TravelApp.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
public class Wind {
    private double speed;
    private int deg;
    private double gust;
}
