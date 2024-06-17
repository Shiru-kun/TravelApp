package co.mz.vodafone.TravelApp.services;

import co.mz.vodafone.TravelApp.dtos.LoginResponse;
import co.mz.vodafone.TravelApp.dtos.UserAccountDto;
import co.mz.vodafone.TravelApp.exceptions.BadRequestException;
import co.mz.vodafone.TravelApp.exceptions.ConflictException;
import co.mz.vodafone.TravelApp.exceptions.InternalServerErrorException;
import co.mz.vodafone.TravelApp.exceptions.NotFoundException;
import co.mz.vodafone.TravelApp.interfaces.IAuthenticationService;
import co.mz.vodafone.TravelApp.repositories.UserAccountRepository;
import co.mz.vodafone.TravelApp.entity.UserAccount;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AuthenticationServiceImpl implements IAuthenticationService {
    public static final String INTERNAL_SERVER_ERROR = "Internal server error ";
    public static final String USER_WITH_EMAIL = "user with email ";
    public static final String ALREADY_EXISTS = " already exists";
    public static final String NOT_AUTHENTICADED = "Not authenticaded";
    public static final String NOT_FOUND_USER_WITH_EMAIL = "not found user with email ";
    public static final String USER_EMAIL_CANNOT_BE_NULL = "user email cannot be null";
    public static final String USER_PASSWORD_CANNOT_BE_NULL = "user password cannot be null";
    private static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    public static final String INVALID_EMAIL_FORMAT = "invalid email format";
    public static final String WRONG_CREDENTIALS = "Wrong credentials";
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

    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public UserAccount signup(UserAccountDto input) {
        Optional<UserAccount> userAccount = Optional.empty();
        var email = input.getEmail();
        var password = input.getPassword();
        var fullname = input.getFullname();

        if (email == null || email.isEmpty()) {
            throw new BadRequestException(USER_EMAIL_CANNOT_BE_NULL);
        }
        if (!isValidEmail(email)) {
            throw new BadRequestException(INVALID_EMAIL_FORMAT);
        }
        if (password == null || password.isEmpty()) {
            throw new BadRequestException(USER_PASSWORD_CANNOT_BE_NULL);
        }
        try {
            userAccount = userRepository.findByEmail(email);
        } catch (Exception ex) {
            throw new InternalServerErrorException(INTERNAL_SERVER_ERROR.concat(String.valueOf(ex.getLocalizedMessage())));
        }
        if (userAccount.isPresent()) {
            throw new ConflictException(USER_WITH_EMAIL.concat(email.concat(ALREADY_EXISTS)));
        }
        UserAccount user = new UserAccount();
        user.setFullname(fullname);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    @Override
    public LoginResponse login(UserAccountDto dto) {
        boolean isAuthenticated = false;
        try {
            var autenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.getEmail(),
                            dto.getPassword()
                    )
            );
            isAuthenticated = autenticate.isAuthenticated();
        } catch (BadCredentialsException ex) {
            throw new BadRequestException(WRONG_CREDENTIALS);
        } catch (Exception ex) {
            throw ex;
        }

        if (!isAuthenticated) {
            throw new NotFoundException(NOT_AUTHENTICADED);
        }
        var userAccount = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER_WITH_EMAIL.concat(dto.getEmail())));
        String jwtToken = jwtService.generateToken(userAccount);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        loginResponse.setToken(jwtToken);
        dto.setPassword(null);
        dto.setFullname(userAccount.getFullname());
        loginResponse.setUserAccountDto(dto);
        return loginResponse;
    }
}
