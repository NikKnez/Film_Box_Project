package FilmBox;

import FilmBox.controllers.PublicController;
import FilmBox.entities.Film;
import FilmBox.entities.User;
import FilmBox.service.FilmService;
import FilmBox.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class PublicControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private FilmService filmService;

    @InjectMocks
    private PublicController publicController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetNumberOfUsers() {
        // Arrange
        int expectedNumberOfUsers = 5;
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < expectedNumberOfUsers; i++) {
            userList.add(new User()); // Add User objects to the list
        }
        when(userService.getUsers()).thenReturn(userList);

        // Act
        int result = publicController.getNumberOfUsers();

        // Assert
        assertEquals(expectedNumberOfUsers, result);
    }

    @Test
    void testGetNumberOfFilms() {
        // Arrange
        int expectedNumberOfFilms = 10;
        List<Film> filmList = new ArrayList<>();
        for (int i = 0; i < expectedNumberOfFilms; i++) {
            filmList.add(new Film()); // Add Film objects to the list
        }
        when(filmService.getFilms()).thenReturn(filmList);

        // Act
        int result = publicController.getNumberOfFilms();

        // Assert
        assertEquals(expectedNumberOfFilms, result);
    }
}
