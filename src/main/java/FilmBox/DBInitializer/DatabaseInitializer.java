package FilmBox.DBInitializer;

import FilmBox.entities.Film;
import FilmBox.entities.User;
import FilmBox.security.OAuth2.OAuth2Provider;
import FilmBox.security.WebSecurityConfig;
import FilmBox.service.FilmService;
import FilmBox.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final UserService userService;
    private final FilmService filmService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!userService.getUsers().isEmpty()) {
            return;
        }
        USERS.forEach(user -> {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.saveUser(user);
        });
        FILMS.forEach(filmService::saveFilm);
        log.info("Database initialized");
    }

    private static final List<User> USERS = Arrays.asList(
            new User("admin", "admin", "Admin", "admin@test.com",
                    WebSecurityConfig.ADMIN, null, OAuth2Provider.LOCAL, "1"),
            new User("user", "user", "User", "user@test.com",
                    WebSecurityConfig.USER, null, OAuth2Provider.LOCAL, "2")
    );

    private static final List<Film> FILMS = Arrays.asList(
            new Film("tt0120737", "The Lord of the Rings: The Fellowship of the Ring",
                    "https://m.media-amazon.com/images/M/MV5BN2EyZjM3NzUtNWUzMi00MTgxLWI0NTctMzY4M2VlOTdjZWRiXkEyXkFqcGdeQXVyNDUzOTQ5MjY@._V1_FMjpg_UX1000_.jpg")
    );

}
