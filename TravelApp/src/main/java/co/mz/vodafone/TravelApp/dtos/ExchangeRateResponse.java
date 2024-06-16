package co.mz.vodafone.TravelApp.dtos;

import java.util.Map;
import lombok.Data;

@Data
public class ExchangeRateResponse {
    private boolean success;
    private long timestamp;
    private String base;
    private String date;
    private Map<String, Object> rates;
}
