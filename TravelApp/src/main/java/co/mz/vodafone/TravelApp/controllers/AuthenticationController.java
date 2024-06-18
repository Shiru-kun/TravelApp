package co.mz.vodafone.TravelApp.controllers;

import co.mz.vodafone.TravelApp.dtos.ErrorResponse;
import co.mz.vodafone.TravelApp.dtos.LoginResponse;
import co.mz.vodafone.TravelApp.dtos.UserAccountDto;
import co.mz.vodafone.TravelApp.interfaces.IAuthenticationService;
import co.mz.vodafone.TravelApp.entity.UserAccount;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Authentication", description = "User login and signup")
@CrossOrigin
@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    public static final String USER_LOGIN_ENDPOINT = "User login endpoint";
    public static final String REGISTER_USER_ENDPOINT = "Register user endpoint";
    private IAuthenticationService _authenticationService;

    public AuthenticationController(IAuthenticationService authenticationService) {
        _authenticationService = authenticationService;
    }

    @Operation(
            summary = REGISTER_USER_ENDPOINT,
            tags = {"Authentication"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = UserAccount.class), mediaType = "application/json")}),
    })
    @PostMapping("/signup")
    public ResponseEntity<UserAccount> register(@RequestBody UserAccountDto dto) {
        UserAccount userAccount = _authenticationService.signup(dto);
        return ResponseEntity.ok(userAccount);
    }

    @Operation(
            summary = USER_LOGIN_ENDPOINT,
            tags = {"Authentication"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = LoginResponse.class), mediaType = "application/json")}),
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody UserAccountDto dto) {
        var loginResponse = _authenticationService.login(dto);
        return ResponseEntity.ok(loginResponse);
    }
}
