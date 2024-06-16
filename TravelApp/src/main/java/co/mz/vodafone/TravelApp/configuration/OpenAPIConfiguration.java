package co.mz.vodafone.TravelApp.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfiguration {
    public static final String URL = "http://localhost:";
    public static final String DEVELOPMENT = "Development";
    public static final String VODAFONE_CHALLENGE_API_FOR_TRAVEL_ASSISTANT_APP = "Vodafone challenge API for travel assistant app";
    @Value("${server.port}")
    private long serverPort;

    @Bean
  public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl(URL.concat(String.valueOf(serverPort)));
        server.setDescription(DEVELOPMENT);
        Info information = new Info()
                .title(VODAFONE_CHALLENGE_API_FOR_TRAVEL_ASSISTANT_APP)
                .version("1.0")
                .description(VODAFONE_CHALLENGE_API_FOR_TRAVEL_ASSISTANT_APP);
             //   .contact(myContact);
        return new OpenAPI().info(information).servers(List.of(server));
    }
}

