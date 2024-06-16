package co.mz.vodafone.TravelApp.dtos;
import lombok.Data;

@Data
public class WorldBankData {
    private Indicator indicator;
    private Country country;
    private String countryiso3code;
    private String date;
    private Double value;
    private String unit;
    private String obsStatus;
    private int decimal;
}
