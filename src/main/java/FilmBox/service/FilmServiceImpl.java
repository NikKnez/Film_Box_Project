package FilmBox.service;

import FilmBox.entities.Film;
import FilmBox.exception.FilmNotFoundException;
import FilmBox.repositories.FilmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepository;

    @Override
    public List<Film> getFilms() {
        return filmRepository.findAllByOrderByTitle();
    }

    @Override
    public List<Film> getFilmsContainingText(String text) {
        return filmRepository.findByImdbContainingOrTitleContainingIgnoreCaseOrderByTitle(text, text);
    }

    @Override
    public Film validateAndGetFilm(String imdb) {
        return filmRepository.findById(imdb)
                .orElseThrow(() -> new FilmNotFoundException(String.format("Film with imdb %s not found", imdb)));
    }

    @Override
    public Film saveFilm(Film film) {
        return filmRepository.save(film);
    }

    @Override
    public void deleteFilm(Film film) {
        filmRepository.delete(film);
    }

}
