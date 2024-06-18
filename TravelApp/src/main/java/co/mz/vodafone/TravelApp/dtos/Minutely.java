package co.mz.vodafone.TravelApp.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
public class Minutely {
    private long dt;
    private double precipitation;
}
