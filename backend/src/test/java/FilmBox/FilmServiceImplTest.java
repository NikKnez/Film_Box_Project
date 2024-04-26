package FilmBox;

import FilmBox.entities.Film;
import FilmBox.exception.FilmNotFoundException;
import FilmBox.repositories.FilmRepository;
import FilmBox.service.FilmServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FilmServiceImplTest {

    @Mock
    private FilmRepository filmRepository;

    @InjectMocks
    private FilmServiceImpl filmService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetFilms() {
        // Arrange
        List<Film> expectedFilms = List.of(new Film(), new Film());
        when(filmRepository.findAllByOrderByTitle()).thenReturn(expectedFilms);

        // Act
        List<Film> result = filmService.getFilms();

        // Assert
        assertEquals(expectedFilms.size(), result.size());
        assertEquals(expectedFilms, result);
    }

    @Test
    void testGetFilmsContainingText() {
        // Arrange
        String searchText = "sample";
        List<Film> expectedFilms = List.of(new Film(), new Film());
        when(filmRepository.findByImdbContainingOrTitleContainingIgnoreCaseOrderByTitle(searchText, searchText))
                .thenReturn(expectedFilms);

        // Act
        List<Film> result = filmService.getFilmsContainingText(searchText);

        // Assert
        assertEquals(expectedFilms.size(), result.size());
        assertEquals(expectedFilms, result);
    }

    @Test
    void testValidateAndGetFilm() {
        // Arrange
        String imdb = "sample-imdb";
        Film expectedFilm = new Film();
        when(filmRepository.findById(imdb)).thenReturn(Optional.of(expectedFilm));

        // Act
        Film result = filmService.validateAndGetFilm(imdb);

        // Assert
        assertNotNull(result);
        assertEquals(expectedFilm, result);
    }

    @Test
    void testValidateAndGetFilm_NotFound() {
        // Arrange
        String imdb = "unknown-imdb";
        when(filmRepository.findById(imdb)).thenReturn(Optional.empty());

        // Act & Assert
        FilmNotFoundException exception = assertThrows(FilmNotFoundException.class, () -> {
            filmService.validateAndGetFilm(imdb);
        });

        assertEquals(String.format("Film with imdb %s not found", imdb), exception.getMessage());
    }

    @Test
    void testSaveFilm() {
        // Arrange
        Film film = new Film();
        when(filmRepository.save(film)).thenReturn(film);

        // Act
        Film result = filmService.saveFilm(film);

        // Assert
        assertNotNull(result);
        assertEquals(film, result);
    }

    @Test
    void testDeleteFilm() {
        // Arrange
        Film film = new Film();

        // Act
        filmService.deleteFilm(film);

        // Assert
        verify(filmRepository, times(1)).delete(film);
    }
}

