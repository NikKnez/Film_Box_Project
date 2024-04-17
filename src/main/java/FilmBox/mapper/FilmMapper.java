package FilmBox.mapper;

import FilmBox.dto.FilmDto;
import FilmBox.entities.CreateFilmRequest;
import FilmBox.entities.Film;

public interface FilmMapper {

    Film toFilm(CreateFilmRequest createFilmRequest);

    FilmDto toFilmDto(Film film);

}
