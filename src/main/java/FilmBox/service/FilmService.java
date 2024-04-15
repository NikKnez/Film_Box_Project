package FilmBox.service;

import FilmBox.entities.Film;

import java.util.List;

public interface FilmService {

    List<Film> getFilms();

    List<Film> getFilmsContainingText(String text);

    Film validateAndGetFilm(String imdb);

    Film saveFilm(Film film);

    void deleteFilm(Film film);

}
