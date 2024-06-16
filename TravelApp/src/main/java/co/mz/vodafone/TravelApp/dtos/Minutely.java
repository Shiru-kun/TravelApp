package co.mz.vodafone.TravelApp.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Data
public class Minutely {
    private long dt;
    private double precipitation;
}
