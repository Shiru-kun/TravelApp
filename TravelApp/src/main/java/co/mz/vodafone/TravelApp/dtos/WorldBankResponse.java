package co.mz.vodafone.TravelApp.dtos;

import lombok.Data;

import java.util.List;

@Data
public class WorldBankResponse {
    private WorldBankMetadataResponse metadata;
    private List<WorldBankData> data;
}
