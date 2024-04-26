package FilmBox;

import FilmBox.security.CustomUserDetails;
import FilmBox.security.TokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class TokenProviderTest {

    @Mock
    private Authentication authentication;

    @Mock
    private CustomUserDetails userDetails;

    private TokenProvider tokenProvider;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tokenProvider = new TokenProvider();
        tokenProvider.jwtSecret = "test-secret-key-1234567890123456-test-secret-key-1234567890123456";
        tokenProvider.jwtExpirationMinutes = 60L;
    }

    @Test
    void generateToken() {

        // Mock the authentication and userDetails
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser");
        when(userDetails.getName()).thenReturn("Test User");
        when(userDetails.getEmail()).thenReturn("test@example.com");

        // Generate the token
        String token = tokenProvider.generate(authentication);

        // Validate the token
        Jws<Claims> jws = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(tokenProvider.jwtSecret.getBytes()))
                .build().parseSignedClaims(token);

        assertEquals("testUser", jws.getPayload().getSubject());
        assertEquals("Test User", jws.getPayload().get("name"));
        assertEquals("test@example.com", jws.getPayload().get("email"));
    }

    @Test
    void validateTokenAndGetJws_ExpiredToken() {
        // Generate an expired token
        String token = Jwts.builder()
                .subject("testUser")
                .expiration(Date.from(ZonedDateTime.now().minusMinutes(1).toInstant()))
                .signWith(Keys.hmacShaKeyFor(tokenProvider.jwtSecret.getBytes()))
                .compact();

        // Validate the token
        Optional<Jws<Claims>> jwsOptional = tokenProvider.validateTokenAndGetJws(token);

        assertTrue(jwsOptional.isEmpty());
    }
}
