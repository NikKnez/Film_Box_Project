package FilmBox.repositories;

import FilmBox.entities.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, String> {

    List<Film> findAllByOrderByTitle();

    List<Film> findByImdbContainingOrTitleContainingIgnoreCaseOrderByTitle(String imdb, String title);
}
