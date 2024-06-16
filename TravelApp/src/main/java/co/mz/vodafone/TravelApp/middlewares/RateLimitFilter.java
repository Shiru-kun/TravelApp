package co.mz.vodafone.TravelApp.middlewares;

import co.mz.vodafone.TravelApp.exceptions.TooManyRequestsException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class RateLimitFilter extends OncePerRequestFilter {
    public static final String EXCEED_NUMBER_OF_REQUESTS_WAIT = "Exceed number of requests, wait ";
    @Value("${travel-app.rate-limit.max-requests}")
    private  int maxRequests;

    @Value("${travel-app.rate-limit.time-window}")
    private long timeWindow;

    private static final Map<String, Queue<Long>> requestMap = new ConcurrentHashMap<>();
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Duration TIME_WINDOW = Duration.ofMinutes(timeWindow);
        int MAX_REQUESTS = maxRequests;
        String ipAddress = request.getRemoteAddr();
        Queue<Long> requestQueue = requestMap.computeIfAbsent(ipAddress, k -> new ConcurrentLinkedQueue<>());

        long now = System.currentTimeMillis();
        requestQueue.add(now);

        while (!requestQueue.isEmpty() && now - requestQueue.peek() > TIME_WINDOW.toMillis()) {
            requestQueue.poll();
        }

        if (requestQueue.size() <= MAX_REQUESTS) {
            filterChain.doFilter(request, response);
        } else {
            throw new TooManyRequestsException(EXCEED_NUMBER_OF_REQUESTS_WAIT.concat(String.valueOf(timeWindow)));

        }
    }
}
