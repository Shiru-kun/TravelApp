package co.mz.vodafone.TravelApp.controllers;
import co.mz.vodafone.TravelApp.dtos.LoginResponse;
import co.mz.vodafone.TravelApp.dtos.UserAccountDto;
import co.mz.vodafone.TravelApp.interfaces.IAuthenticationService;
import co.mz.vodafone.TravelApp.entity.UserAccount;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private  IAuthenticationService _authenticationService;

    public AuthenticationController(IAuthenticationService authenticationService) {
        _authenticationService = authenticationService;
    }
    @Tag(name = "post", description = "User registration")
    @PostMapping("/signup")
    public ResponseEntity<UserAccount> register(@RequestBody UserAccountDto dto) {
        UserAccount userAccount = _authenticationService.signup(dto);
        return ResponseEntity.ok(userAccount);
    }
    @Tag(name = "post", description = "User login")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody UserAccountDto dto) {
        var loginResponse =_authenticationService.login(dto);
        return ResponseEntity.ok(loginResponse);
    }
}
