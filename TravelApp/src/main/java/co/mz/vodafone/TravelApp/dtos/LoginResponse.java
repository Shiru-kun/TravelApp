package co.mz.vodafone.TravelApp.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class LoginResponse {
    private String token;
    private long expiresIn;
    private UserAccountDto userAccountDto;
}
