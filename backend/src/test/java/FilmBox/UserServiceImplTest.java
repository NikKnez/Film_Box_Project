package FilmBox;

import FilmBox.entities.User;
import FilmBox.exception.UserNotFoundException;
import FilmBox.repositories.UserRepository;
import FilmBox.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUsers() {
        // Arrange
        List<User> expectedUsers = List.of(new User(), new User()); // Create a list of User objects
        when(userRepository.findAll()).thenReturn(expectedUsers);

        // Act
        List<User> result = userServiceImpl.getUsers();

        // Assert
        assertEquals(expectedUsers.size(), result.size());
        assertEquals(expectedUsers, result);
    }

    @Test
    void testGetUserByUsername() {
        // Arrange
        String username = "testuser";
        User expectedUser = new User(); // Create a User object
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(expectedUser));

        // Act
        Optional<User> result = userServiceImpl.getUserByUsername(username);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedUser, result.get());
    }

    @Test
    void testGetUserByEmail() {
        // Arrange
        String email = "test@example.com";
        User expectedUser = new User(); // Create a User object
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(expectedUser));

        // Act
        Optional<User> result = userServiceImpl.getUserByEmail(email);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedUser, result.get());
    }

    @Test
    void testHasUserWithUsername() {
        // Arrange
        String username = "testuser";
        when(userRepository.existsByUsername(username)).thenReturn(true);

        // Act
        boolean result = userServiceImpl.hasUserWithUsername(username);

        // Assert
        assertTrue(result);
    }

    @Test
    void testHasUserWithEmail() {
        // Arrange
        String email = "test@example.com";
        when(userRepository.existsByEmail(email)).thenReturn(true);

        // Act
        boolean result = userServiceImpl.hasUserWithEmail(email);

        // Assert
        assertTrue(result);
    }

    @Test
    void testValidateAndGetUserByUsername() {
        // Arrange
        String username = "testuser";
        User expectedUser = new User(); // Create a User object
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(expectedUser));

        // Act
        User result = userServiceImpl.validateAndGetUserByUsername(username);

        // Assert
        assertNotNull(result);
        assertEquals(expectedUser, result);
    }

    @Test
    void testValidateAndGetUserByUsername_NotFound() {
        // Arrange
        String username = "unknownUser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act & Assert
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            userServiceImpl.validateAndGetUserByUsername(username);
        });

        assertEquals(String.format("User with username %s not found", username), exception.getMessage());
    }

    @Test
    void testSaveUser() {
        // Arrange
        User user = new User(); // Create a User object
        when(userRepository.save(user)).thenReturn(user);

        // Act
        User result = userServiceImpl.saveUser(user);

        // Assert
        assertNotNull(result);
        assertEquals(user, result);
    }

    @Test
    void testDeleteUser() {
        // Arrange
        User user = new User(); // Create a User object

        // Act
        userServiceImpl.deleteUser(user);

        // Assert
        verify(userRepository, times(1)).delete(user);
    }
}

