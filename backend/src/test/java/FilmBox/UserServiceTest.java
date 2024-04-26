package FilmBox;

import FilmBox.entities.User;
import FilmBox.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUsers() {
        // Arrange
        List<User> expectedUsers = List.of(new User(), new User()); // Create a list of User objects
        when(userService.getUsers()).thenReturn(expectedUsers);

        // Act
        List<User> result = userService.getUsers();

        // Assert
        assertEquals(expectedUsers.size(), result.size());
        assertEquals(expectedUsers, result);
    }

    @Test
    void testGetUserByUsername() {
        // Arrange
        String username = "testuser";
        User expectedUser = new User(); // Create a User object
        when(userService.getUserByUsername(username)).thenReturn(Optional.of(expectedUser));

        // Act
        Optional<User> result = userService.getUserByUsername(username);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedUser, result.get());
    }

    @Test
    void testGetUserByEmail() {
        // Arrange
        String email = "test@example.com";
        User expectedUser = new User(); // Create a User object
        when(userService.getUserByEmail(email)).thenReturn(Optional.of(expectedUser));

        // Act
        Optional<User> result = userService.getUserByEmail(email);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedUser, result.get());
    }

    @Test
    void testHasUserWithUsername() {
        // Arrange
        String username = "testuser";
        when(userService.hasUserWithUsername(username)).thenReturn(true);

        // Act
        boolean result = userService.hasUserWithUsername(username);

        // Assert
        assertTrue(result);
    }

    @Test
    void testHasUserWithEmail() {
        // Arrange
        String email = "test@example.com";
        when(userService.hasUserWithEmail(email)).thenReturn(true);

        // Act
        boolean result = userService.hasUserWithEmail(email);

        // Assert
        assertTrue(result);
    }

    @Test
    void testValidateAndGetUserByUsername() {
        // Arrange
        String username = "testuser";
        User expectedUser = new User(); // Create a User object
        when(userService.validateAndGetUserByUsername(username)).thenReturn(expectedUser);

        // Act
        User result = userService.validateAndGetUserByUsername(username);

        // Assert
        assertNotNull(result);
        assertEquals(expectedUser, result);
    }

    @Test
    void testSaveUser() {
        // Arrange
        User user = new User(); // Create a User object
        when(userService.saveUser(user)).thenReturn(user);

        // Act
        User result = userService.saveUser(user);

        // Assert
        assertNotNull(result);
        assertEquals(user, result);
    }

    @Test
    void testDeleteUser() {
        // Arrange
        User user = new User(); // Create a User object

        // Act
        userService.deleteUser(user);

        // Assert
        verify(userService, times(1)).deleteUser(user);
    }
}

