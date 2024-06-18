package co.mz.vodafone.TravelApp.middlewares;

import co.mz.vodafone.TravelApp.dtos.ErrorResponse;
import co.mz.vodafone.TravelApp.exceptions.ForbiddenException;
import co.mz.vodafone.TravelApp.exceptions.InternalServerErrorException;
import co.mz.vodafone.TravelApp.exceptions.NotAuthorizedException;
import co.mz.vodafone.TravelApp.exceptions.TooManyRequestsException;
import co.mz.vodafone.TravelApp.services.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    public static final String CONTENT_TYPE = "application/json";
    public static final String INTERNAL_SERVER_ERROR = "Internal server error ";
    public static final String EXPIRED_SESSION = "Expired session ";
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER_ = "Bearer ";
    @Autowired
    private JwtService _jwtService;
    @Autowired
    private UserDetailsService _userDetailsService;
    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader(AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith(BEARER_)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String jwt = authHeader.substring(7);
            final String userEmail = _jwtService.extractUsername(jwt);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (userEmail != null && authentication == null) {
                UserDetails userDetails = _userDetailsService.loadUserByUsername(userEmail);

                if (_jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException ex) {
            throw new ForbiddenException(EXPIRED_SESSION.concat(ex.getMessage()));
        } catch (Exception ex) {
            response.setContentType(CONTENT_TYPE);
            handlerExceptionResolver.resolveException(request, response, null, new InternalServerErrorException(INTERNAL_SERVER_ERROR.concat(ex.getMessage())));

        }
    }
}
