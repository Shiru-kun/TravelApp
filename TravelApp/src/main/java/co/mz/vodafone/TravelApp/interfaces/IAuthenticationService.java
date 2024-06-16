package co.mz.vodafone.TravelApp.interfaces;

import co.mz.vodafone.TravelApp.dtos.LoginResponse;
import co.mz.vodafone.TravelApp.dtos.UserAccountDto;
import co.mz.vodafone.TravelApp.entity.UserAccount;

public interface IAuthenticationService {
    public UserAccount signup(UserAccountDto input);
    public LoginResponse login(UserAccountDto dto);
}
