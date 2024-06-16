package co.mz.vodafone.TravelApp.integrations;

import co.mz.vodafone.TravelApp.controllers.ExchangeRateController;
import co.mz.vodafone.TravelApp.dtos.ExchangeRateResponse;
import co.mz.vodafone.TravelApp.feignclients.ExchangeRateClient;
import co.mz.vodafone.TravelApp.interfaces.IExchangeRateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ExchangeRateController.class)
@AutoConfigureMockMvc
class ExchangeRateControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExchangeRateClient exchangeRateClient;

    @MockBean
    private IExchangeRateService exchangeRateService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExchangeRateEndpoint() throws Exception {
        mockMvc.perform(get("/1.0/exchange-rate/MZN"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetExchangeRateFor() throws Exception {
        ExchangeRateResponse exchangeRateResponse = new ExchangeRateResponse();
        exchangeRateResponse.setSuccess(true);
        exchangeRateResponse.setBase("EUR");
        exchangeRateResponse.setDate("2024-06-15");
        exchangeRateResponse.setRates(Map.of("MZN", 68.280013));

        when(exchangeRateClient.getExchangeRateByCountrySymbol("MZN"))
                .thenReturn(exchangeRateResponse);

        mockMvc.perform(get("/1.0/exchange-rate/MZN")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.base").value("EUR"))
                .andExpect(jsonPath("$.date").value("2024-06-15"))
                .andExpect(jsonPath("$.rates.MZN").value(68.280013));
    }

    @Test
    void testGetDefaultExchangeRate() throws Exception {
        ExchangeRateResponse exchangeRateResponse = new ExchangeRateResponse();
        exchangeRateResponse.setSuccess(true);
        exchangeRateResponse.setBase("EUR");
        exchangeRateResponse.setDate("2024-06-15");
        exchangeRateResponse.setRates(Map.of("MZN", 68.280013));

        when(exchangeRateClient.getExchangeRateByCountrySymbol(""))
                .thenReturn(exchangeRateResponse);

        mockMvc.perform(get("/1.0/exchange-rate")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.base").value("EUR"))
                .andExpect(jsonPath("$.date").value("2024-06-15"))
                .andExpect(jsonPath("$.rates.MZN").value(68.280013));
    }
}
