package co.mz.vodafone.TravelApp.dtos;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
public class Weather {
    private int id;
    private String main;
    private String description;
    private String icon;
}
