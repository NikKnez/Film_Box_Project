package FilmBox.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "films")
public class Film {

    @Id
    private String imdb;

    private String title;
    private String poster;

    private ZonedDateTime createdAt;

    public Film(String imdb, String title, String poster) {
        this.imdb = imdb;
        this.title = title;
        this.poster = poster;
    }

    @PrePersist
    public void onPrePersist() {
        createdAt = ZonedDateTime.now();
    }
}

