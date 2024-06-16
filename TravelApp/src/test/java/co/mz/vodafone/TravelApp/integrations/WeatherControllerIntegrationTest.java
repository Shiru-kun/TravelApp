package co.mz.vodafone.TravelApp.integrations;

import co.mz.vodafone.TravelApp.dtos.WeatherData;
import co.mz.vodafone.TravelApp.feignclients.WeatherClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class WeatherControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherClient weatherClient;

    @Test
    void testGetWeatherFor() throws Exception {
        WeatherData weatherData = new WeatherData();
        weatherData.setName("Maputo");

        when(weatherClient.getWeatherData(anyDouble(),anyDouble()))
                .thenReturn(weatherData);

        mockMvc.perform(get("/1.0/weather/Maputo")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Maputo"));

    }

    @Test
    void testGetDefaultWeather() throws Exception {
        WeatherData weatherData = new WeatherData();
        weatherData.setName("Maputo");

        when(weatherClient.getWeatherData(anyDouble(), anyDouble()))
                .thenReturn(weatherData);

        mockMvc.perform(get("/1.0/weather")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Maputo"));

    }
}
