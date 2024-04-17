package FilmBox.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateFilmRequest {

    @Schema(example = "tt0111161")
    @NotBlank
    private String imdb;

    @Schema(example = "The Shawshank Redemption")
    @NotBlank
    private String title;

    @Schema(example = "https://m.media-amazon.com/images/M/MV5BNDE3ODcxYzMtY2YzZC00NmNlLWJiNDMtZDViZWM2MzIxZDYwXkEyXkFqcGdeQXVyNjAwNDUxODI@._V1_.jpg")
    private String poster;
}
