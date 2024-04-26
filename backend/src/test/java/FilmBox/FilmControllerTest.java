package FilmBox;

import FilmBox.controllers.FilmController;
import FilmBox.dto.FilmDto;
import FilmBox.entities.CreateFilmRequest;
import FilmBox.entities.Film;
import FilmBox.mapper.FilmMapper;
import FilmBox.service.FilmService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class FilmControllerTest {

    @Mock
    private FilmService filmService;

    @Mock
    private FilmMapper filmMapper;

    @InjectMocks
    private FilmController filmController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetFilms() {
        // Arrange
        List<Film> films = new ArrayList<>();
        Film film1 = new Film();
        Film film2 = new Film();
        films.add(film1);
        films.add(film2);

        List<FilmDto> filmDtos = new ArrayList<>();
        FilmDto filmDto1 = new FilmDto("imdb", "title", "poster", "createdAt");
        FilmDto filmDto2 = new FilmDto("imdb", "title", "poster", "createdAt");
        filmDtos.add(filmDto1);
        filmDtos.add(filmDto2);

        when(filmService.getFilms()).thenReturn(films);
        when(filmMapper.toFilmDto(film1)).thenReturn(filmDto1);
        when(filmMapper.toFilmDto(film2)).thenReturn(filmDto2);

        // Act
        List<FilmDto> result = filmController.getFilms(null);

        // Assert
        assertEquals(filmDtos, result);
        verify(filmService).getFilms();
        verify(filmMapper, times(2)).toFilmDto(any(Film.class));
    }

    @Test
    void testGetFilmsWithText() {
        // Arrange
        String text = "test";
        List<Film> films = new ArrayList<>();
        Film film1 = new Film();
        Film film2 = new Film();
        films.add(film1);
        films.add(film2);

        List<FilmDto> filmDtos = new ArrayList<>();
        FilmDto filmDto1 = new FilmDto("imdb", "title", "poster", "createdAt");
        FilmDto filmDto2 = new FilmDto("imdb", "title", "poster", "createdAt");
        filmDtos.add(filmDto1);
        filmDtos.add(filmDto2);

        when(filmService.getFilmsContainingText(text)).thenReturn(films);
        when(filmMapper.toFilmDto(film1)).thenReturn(filmDto1);
        when(filmMapper.toFilmDto(film2)).thenReturn(filmDto2);

        // Act
        List<FilmDto> result = filmController.getFilms(text);

        // Assert
        assertEquals(filmDtos, result);
        verify(filmService).getFilmsContainingText(text);
        verify(filmMapper, times(2)).toFilmDto(any(Film.class));
    }

    @Test
    void testCreateFilm() {
        // Arrange
        CreateFilmRequest createFilmRequest = new CreateFilmRequest();
        Film film = new Film();
        FilmDto filmDto = new FilmDto("imdb", "title", "poster", "createdAt");

        when(filmMapper.toFilm(createFilmRequest)).thenReturn(film);
        when(filmService.saveFilm(film)).thenReturn(film);
        when(filmMapper.toFilmDto(film)).thenReturn(filmDto);

        // Act
        FilmDto result = filmController.createFilm(createFilmRequest);

        // Assert
        assertNotNull(result);
        verify(filmMapper).toFilm(createFilmRequest);
        verify(filmService).saveFilm(film);
        verify(filmMapper).toFilmDto(film);
    }

    @Test
    void testDeleteFilm() {
        // Arrange
        String imdb = "tt1234567";
        Film film = new Film();
        FilmDto filmDto = new FilmDto("imdb", "title", "poster", "createdAt");

        when(filmService.validateAndGetFilm(imdb)).thenReturn(film);
        when(filmMapper.toFilmDto(film)).thenReturn(filmDto);

        // Act
        FilmDto result = filmController.deleteFilm(imdb);

        // Assert
        assertEquals(filmDto, result);
        verify(filmService).validateAndGetFilm(imdb);
        verify(filmService).deleteFilm(film);
        verify(filmMapper).toFilmDto(film);
    }
}

