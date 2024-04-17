package FilmBox.controllers;

import FilmBox.dto.FilmDto;
import FilmBox.entities.CreateFilmRequest;
import FilmBox.entities.Film;
import FilmBox.mapper.FilmMapper;
import FilmBox.service.FilmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static FilmBox.config.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/films")
public class FilmController {

    private final FilmService filmService;
    private final FilmMapper filmMapper;

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping
    public List<FilmDto> getFilms(@RequestParam(value = "text", required = false) String text) {
        List<Film> films = (text == null) ? filmService.getFilms() : filmService.getFilmsContainingText(text);
        return films.stream()
                .map(filmMapper::toFilmDto)
                .collect(Collectors.toList());
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public FilmDto createFilm(@Valid @RequestBody CreateFilmRequest createFilmRequest) {
        Film film = filmMapper.toFilm(createFilmRequest);
        return filmMapper.toFilmDto(filmService.saveFilm(film));
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @DeleteMapping("/{imdb}")
    public FilmDto deleteFilm(@PathVariable String imdb) {
        Film film = filmService.validateAndGetFilm(imdb);
        filmService.deleteFilm(film);
        return filmMapper.toFilmDto(film);
    }

}
