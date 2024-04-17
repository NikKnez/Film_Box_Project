package FilmBox.mapper;

import FilmBox.dto.FilmDto;
import FilmBox.entities.CreateFilmRequest;
import FilmBox.entities.Film;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class FilmMapperImpl implements FilmMapper {

    @Override
    public Film toFilm(CreateFilmRequest createFilmRequest) {
        if (createFilmRequest == null) {
            return null;
        }
        return new Film(createFilmRequest.getImdb(), createFilmRequest.getTitle(), createFilmRequest.getPoster());
    }

    @Override
    public FilmDto toFilmDto(Film film) {
        if (film == null) {
            return null;
        }
        return new FilmDto(film.getImdb(), film.getTitle(), film.getPoster(), DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(film.getCreatedAt()));
    }

}
