package co.mz.vodafone.TravelApp.dtos;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserAccountDto {

    private String email;

    private String password;

    private String fullname;
}
