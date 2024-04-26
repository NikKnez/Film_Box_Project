package FilmBox.entities;

public record AuthResponse(String accessToken) {
    public String getToken() {
        return accessToken;
    }
}
