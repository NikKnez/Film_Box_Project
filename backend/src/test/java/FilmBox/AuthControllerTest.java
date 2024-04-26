package FilmBox;

import FilmBox.controllers.AuthController;
import FilmBox.entities.AuthResponse;
import FilmBox.entities.LoginRequest;
import FilmBox.entities.SignUpRequest;
import FilmBox.entities.User;
import FilmBox.exception.DuplicatedUserInfoException;
import FilmBox.security.TokenProvider;
import FilmBox.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenProvider tokenProvider;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginSuccess() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        Authentication authentication = mock(Authentication.class);
        String token = "testToken";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(tokenProvider.generate(authentication)).thenReturn(token);

        // Act
        AuthResponse response = authController.login(loginRequest);

        // Assert
        assertEquals(token, response.getToken());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenProvider).generate(authentication);
    }

    @Test
    void testSignUpSuccess() {
        // Arrange
        SignUpRequest signUpRequest = new SignUpRequest();
        Authentication authentication = mock(Authentication.class);
        String token = "testToken";

        when(userService.hasUserWithUsername(signUpRequest.getUsername())).thenReturn(false);
        when(userService.hasUserWithEmail(signUpRequest.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(signUpRequest.getPassword())).thenReturn("encodedPassword");
        when(userService.saveUser(any(User.class))).thenReturn(new User());
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(tokenProvider.generate(authentication)).thenReturn(token);

        // Act
        AuthResponse response = authController.signUp(signUpRequest);

        // Assert
        assertEquals(token, response.getToken());
        verify(userService).hasUserWithUsername(signUpRequest.getUsername());
        verify(userService).hasUserWithEmail(signUpRequest.getEmail());
        verify(passwordEncoder).encode(signUpRequest.getPassword());
        verify(userService).saveUser(any(User.class));
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenProvider).generate(authentication);
    }

    @Test
    void testSignUpDuplicateUsername() {
        // Arrange
        String username = "username";
        String password = "password";
        String name = "name";
        String email = "email@example.com";

        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setUsername(username);
        signUpRequest.setPassword(password);
        signUpRequest.setName(name);
        signUpRequest.setEmail(email);

        // Mock the userService behavior to return true when checking for an existing username
        when(userService.hasUserWithUsername(username)).thenReturn(true);

        // Act & Assert
        DuplicatedUserInfoException exception = assertThrows(DuplicatedUserInfoException.class, () -> {
            authController.signUp(signUpRequest);
        });

        // Update the assertion to match the actual error message
        assertEquals(String.format("Username %s already been used", username), exception.getMessage());

        // Verify that userService hasUserWithUsername was called with the correct username
        verify(userService).hasUserWithUsername(username);
    }



}
