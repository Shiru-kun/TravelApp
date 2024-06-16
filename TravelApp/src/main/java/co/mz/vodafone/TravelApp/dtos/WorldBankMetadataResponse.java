package co.mz.vodafone.TravelApp.dtos;
import lombok.Data;

@Data
public class WorldBankMetadataResponse {
    private int page;
    private int pages;
    private int perPage;
    private int total;
    private String sourceid;
    private String lastupdated;
}
