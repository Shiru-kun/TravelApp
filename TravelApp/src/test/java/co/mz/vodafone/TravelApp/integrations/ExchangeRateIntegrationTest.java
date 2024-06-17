package co.mz.vodafone.TravelApp.integrations;

import co.mz.vodafone.TravelApp.TravelAppApplication;
import co.mz.vodafone.TravelApp.dtos.ExchangeRateResponse;
import co.mz.vodafone.TravelApp.dtos.UserAccountDto;
import co.mz.vodafone.TravelApp.feignclients.ExchangeRateClient;
import co.mz.vodafone.TravelApp.interfaces.IAuthenticationService;
import co.mz.vodafone.TravelApp.repositories.UserAccountRepository;
import co.mz.vodafone.TravelApp.utils.JwtTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = TravelAppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@ActiveProfiles("test")
class ExchangeRateControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtTestUtil jwtTestUtil;

    @Autowired
    private IAuthenticationService authenticationService;
    @Autowired
    private UserAccountRepository userAccountRepository;

    @MockBean
    private ExchangeRateClient exchangeRateClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userAccountRepository.deleteAll();
        createTestUser();
    }

    void createTestUser() {
        UserAccountDto userAccountDto = new UserAccountDto();
        userAccountDto.setPassword("123123");
        userAccountDto.setFullname("test");
        String email = "testuser@mail.com";
        userAccountDto.setEmail(email);
        authenticationService.signup(userAccountDto);
    }

    @Test
    void testGetExchangeRateForNotAuthenticated() throws Exception {
        mockMvc.perform(get("/1.0/exchange-rate/MZ")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    void testGetExchangeRateFor() throws Exception {
        String email = "testuser@mail.com";
        String token = jwtTestUtil.generateTestToken(email);

        ExchangeRateResponse exchangeRateResponse = new ExchangeRateResponse();
        exchangeRateResponse.setSuccess(true);
        exchangeRateResponse.setBase("EUR");
        exchangeRateResponse.setDate("2024-06-15");
        exchangeRateResponse.setRates(Map.of("MZN", 68.280013));

        when(exchangeRateClient.getExchangeRateByCountrySymbol("MZN"))
                .thenReturn(exchangeRateResponse);

        mockMvc.perform(get("/1.0/exchange-rate/MZN")
                        .header("Authorization", "Bearer " + token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.base").value("EUR"))
                .andExpect(jsonPath("$.date").value("2024-06-15"))
                .andExpect(jsonPath("$.rates.MZN").value(68.280013));
    }
}
