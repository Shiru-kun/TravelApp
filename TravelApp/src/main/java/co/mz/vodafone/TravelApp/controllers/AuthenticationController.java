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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "Authentication", description = "User login and signup")
@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private IAuthenticationService _authenticationService;

    public AuthenticationController(IAuthenticationService authenticationService) {
        _authenticationService = authenticationService;
    }

    @Operation(
            summary = "Register user endpoint",
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
            summary = "User login endpoint",
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
