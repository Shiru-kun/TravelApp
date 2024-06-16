package co.mz.vodafone.TravelApp.utils;

import co.mz.vodafone.TravelApp.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class JwtTestUtil {

    @Autowired
    private JwtService jwtService;

    public String generateTestToken(String username) {
        UserDetails userDetails = new User(username, "", Collections.emptyList());
        return jwtService.generateToken(userDetails);
    }
}
