package FilmBox.mapper;

import FilmBox.dto.UserDto;
import FilmBox.entities.User;

public interface UserMapper {

    UserDto toUserDto(User user);

}
