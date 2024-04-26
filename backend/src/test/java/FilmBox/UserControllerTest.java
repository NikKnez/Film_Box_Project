package FilmBox;

import FilmBox.controllers.UserController;
import FilmBox.dto.UserDto;
import FilmBox.entities.User;
import FilmBox.mapper.UserMapper;
import FilmBox.security.CustomUserDetails;
import FilmBox.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCurrentUser() {
        // Arrange
        CustomUserDetails currentUser = new CustomUserDetails();
        currentUser.setUsername("testUser");

        User user = new User();
        user.setUsername("testUser");
        UserDto userDto = new UserDto(1L, "username", "name", "email", "role");
        //userDto.username();

        when(userService.validateAndGetUserByUsername(currentUser.getUsername())).thenReturn(user);
        when(userMapper.toUserDto(user)).thenReturn(userDto);

        // Act
        UserDto result = userController.getCurrentUser(currentUser);

        // Assert
        assertEquals(userDto, result);
        verify(userService).validateAndGetUserByUsername(currentUser.getUsername());
        verify(userMapper).toUserDto(user);
    }

    @Test
    void testGetUsers() {
        // Arrange
        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setUsername("user1");
        User user2 = new User();
        user2.setUsername("user2");
        users.add(user1);
        users.add(user2);

        List<UserDto> userDtos = new ArrayList<>();
        UserDto userDto1 = new UserDto(1L, "username", "name", "email", "role");
        //userDto1.username;
        UserDto userDto2 = new UserDto(1L, "username", "name", "email", "role");
        //userDto2.username("user2");
        userDtos.add(userDto1);
        userDtos.add(userDto2);

        when(userService.getUsers()).thenReturn(users);
        when(userMapper.toUserDto(any(User.class))).thenReturn(userDto1, userDto2);

        // Act
        List<UserDto> result = userController.getUsers();

        // Assert
        assertEquals(userDtos, result);
        verify(userService).getUsers();
        verify(userMapper, times(2)).toUserDto(any(User.class));
    }

    @Test
    void testGetUser() {
        // Arrange
        String username = "testUser";
        User user = new User();
        user.setUsername(username);
        UserDto userDto = new UserDto(1L, "username", "name", "email", "role");
        //userDto.setUsername(username);

        when(userService.validateAndGetUserByUsername(username)).thenReturn(user);
        when(userMapper.toUserDto(user)).thenReturn(userDto);

        // Act
        UserDto result = userController.getUser(username);

        // Assert
        assertEquals(userDto, result);
        verify(userService).validateAndGetUserByUsername(username);
        verify(userMapper).toUserDto(user);
    }

    @Test
    void testDeleteUser() {
        // Arrange
        String username = "testUser";
        User user = new User();
        user.setUsername(username);
        UserDto userDto = new UserDto(1L, "username", "name", "email", "role");
        //userDto.setUsername(username);

        when(userService.validateAndGetUserByUsername(username)).thenReturn(user);
        when(userMapper.toUserDto(user)).thenReturn(userDto);

        // Act
        UserDto result = userController.deleteUser(username);

        // Assert
        assertEquals(userDto, result);
        verify(userService).validateAndGetUserByUsername(username);
        verify(userService).deleteUser(user);
        verify(userMapper).toUserDto(user);
    }

}

