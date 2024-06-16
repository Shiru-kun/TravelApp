package co.mz.vodafone.TravelApp.services;

import co.mz.vodafone.TravelApp.dtos.LoginResponse;
import co.mz.vodafone.TravelApp.dtos.UserAccountDto;
import co.mz.vodafone.TravelApp.exceptions.ConflictException;
import co.mz.vodafone.TravelApp.exceptions.InternalServerErrorException;
import co.mz.vodafone.TravelApp.exceptions.NotFoundException;
import co.mz.vodafone.TravelApp.interfaces.IAuthenticationService;
import co.mz.vodafone.TravelApp.repositories.UserAccountRepository;
import co.mz.vodafone.TravelApp.entity.UserAccount;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements IAuthenticationService {
    public static final String INTERNAL_SERVER_ERROR = "Internal server error ";
    public static final String USER_WITH_EMAIL = "user with email ";
    public static final String ALREADY_EXISTS = " already exists";
    public static final String NOT_AUTHENTICADED = "Not authenticaded";
    public static final String NOT_FOUND_USER_WITH_EMAIL = "not found user with email ";
    private final UserAccountRepository userRepository;
    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationServiceImpl(
            UserAccountRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;

    }

    public UserAccount signup(UserAccountDto input) {
        Optional<UserAccount> userAccount = Optional.empty();

        try {
            userAccount = userRepository.findByEmail(input.getEmail());
        }catch (Exception ex){
            throw  new InternalServerErrorException(INTERNAL_SERVER_ERROR.concat(String.valueOf(ex.getLocalizedMessage())));
        }
        if(userAccount.isPresent()){
            throw  new ConflictException(USER_WITH_EMAIL.concat(input.getEmail().concat(ALREADY_EXISTS)));
        }
        UserAccount user = new UserAccount();
        user.setFullname(input.getFullname());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public LoginResponse login(UserAccountDto dto) {
       var autenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getEmail(),
                        dto.getPassword()
                )
        );
        if(!autenticate.isAuthenticated()){
            throw  new NotFoundException(NOT_AUTHENTICADED);
        }
       var userAccount = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(()-> new NotFoundException(NOT_FOUND_USER_WITH_EMAIL.concat(dto.getEmail())));
        String jwtToken = jwtService.generateToken(userAccount);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        loginResponse.setToken(jwtToken);
        dto.setFullname(userAccount.getFullname());
        loginResponse.setUserAccountDto(dto);
        return loginResponse;
    }
}
