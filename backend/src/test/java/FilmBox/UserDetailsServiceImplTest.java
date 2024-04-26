package FilmBox;

import FilmBox.entities.User;
import FilmBox.security.CustomUserDetails;
import FilmBox.security.UserDetailsServiceImpl;
import FilmBox.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class UserDetailsServiceImplTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsername_Success() {
        // Arrange
        String username = "testuser";
        User user = new User();
        user.setId(1L);
        user.setUsername(username);
        user.setPassword("testpassword");
        user.setName("Test User");
        user.setEmail("testuser@example.com");
        user.setRole("ROLE_USER");

        List<SimpleGrantedAuthority> expectedAuthorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole()));

        when(userService.getUserByUsername(username)).thenReturn(Optional.of(user));

        // Act
        CustomUserDetails customUserDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(username);

        // Assert
        assertEquals(user.getId(), customUserDetails.getId());
        assertEquals(user.getUsername(), customUserDetails.getUsername());
        assertEquals(user.getPassword(), customUserDetails.getPassword());
        assertEquals(user.getName(), customUserDetails.getName());
        assertEquals(user.getEmail(), customUserDetails.getEmail());
        assertEquals(expectedAuthorities, customUserDetails.getAuthorities());
    }

    @Test
    void testLoadUserByUsername_UsernameNotFound() {
        // Arrange
        String username = "testuser";

        when(userService.getUserByUsername(username)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername(username);
        });
    }
}

