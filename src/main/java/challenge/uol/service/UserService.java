package challenge.uol.service;

import challenge.uol.model.dto.UserDto;
import challenge.uol.model.dto.UserWeatherDto;

import java.util.List;

public interface UserService {

	UserWeatherDto getUserById(Integer id);

	void createUser(UserDto userDto, String ip);

	void updateUser(Integer id, UserDto userDto);

	void delete(Integer id);

	List<UserWeatherDto> getAllUsers();
}