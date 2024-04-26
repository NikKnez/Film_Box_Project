package FilmBox;

import FilmBox.security.CustomUserDetails;
import FilmBox.security.OAuth2.OAuth2Provider;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomUserDetailsTest {

    @Test
    void testCustomUserDetailsInitialization() {
        // Arrange
        Long id = 1L;
        String username = "testuser";
        String password = "testpassword";
        String name = "Test User";
        String email = "testuser@example.com";
        String avatarUrl = "http://example.com/avatar.jpg";
        OAuth2Provider provider = OAuth2Provider.LOCAL;
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("sub", "testuser");

        CustomUserDetails userDetails = new CustomUserDetails();
        userDetails.setId(id);
        userDetails.setUsername(username);
        userDetails.setPassword(password);
        userDetails.setName(name);
        userDetails.setEmail(email);
        userDetails.setAvatarUrl(avatarUrl);
        userDetails.setProvider(provider);
        userDetails.setAuthorities(authorities);
        userDetails.setAttributes(attributes);

        // Assert
        assertEquals(id, userDetails.getId());
        assertEquals(username, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword());
        assertEquals(name, userDetails.getName());
        assertEquals(email, userDetails.getEmail());
        assertEquals(avatarUrl, userDetails.getAvatarUrl());
        assertEquals(provider, userDetails.getProvider());
        assertEquals(authorities, userDetails.getAuthorities());
        assertEquals(attributes, userDetails.getAttributes());
    }

    @Test
    void testAccountNonExpired() {
        // Arrange
        CustomUserDetails userDetails = new CustomUserDetails();

        // Act & Assert
        assertTrue(userDetails.isAccountNonExpired());
    }

    @Test
    void testAccountNonLocked() {
        // Arrange
        CustomUserDetails userDetails = new CustomUserDetails();

        // Act & Assert
        assertTrue(userDetails.isAccountNonLocked());
    }

    @Test
    void testCredentialsNonExpired() {
        // Arrange
        CustomUserDetails userDetails = new CustomUserDetails();

        // Act & Assert
        assertTrue(userDetails.isCredentialsNonExpired());
    }

    @Test
    void testIsEnabled() {
        // Arrange
        CustomUserDetails userDetails = new CustomUserDetails();

        // Act & Assert
        assertTrue(userDetails.isEnabled());
    }

    @Test
    void testGetNameFromOAuth2User() {
        // Arrange
        CustomUserDetails userDetails = new CustomUserDetails();
        String expectedName = "Test User";
        userDetails.setName(expectedName);

        // Act
        String resultName = userDetails.getName();

        // Assert
        assertEquals(expectedName, resultName);
    }

    @Test
    void testGetAttributesFromOAuth2User() {
        // Arrange
        CustomUserDetails userDetails = new CustomUserDetails();
        Map<String, Object> expectedAttributes = new HashMap<>();
        expectedAttributes.put("sub", "testuser");
        userDetails.setAttributes(expectedAttributes);

        // Act
        Map<String, Object> resultAttributes = userDetails.getAttributes();

        // Assert
        assertEquals(expectedAttributes, resultAttributes);
    }
}
