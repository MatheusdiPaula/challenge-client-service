package challenge.uol.service;

import challenge.uol.model.db.User;
import challenge.uol.model.db.Weather;
import challenge.uol.model.dto.UserDto;
import challenge.uol.model.dto.UserWeatherDto;
import challenge.uol.model.dto.WeatherDto;
import challenge.uol.respository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private GeolocationService geolocationService;
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserWeatherDto getUserById(Integer id) {
		User userEntity = userRepository.findById(id)
				.orElseThrow(
						() -> new RuntimeException("Usuário não encontrado")
				);

		UserWeatherDto userDto = new UserWeatherDto();
		BeanUtils.copyProperties(userEntity, userDto);
		this.setWeather(userEntity, userDto);
		return userDto;
	}

	@Override
	public void createUser(UserDto userDto, String ip) {
		User userEntity = User.builder().build();
		BeanUtils.copyProperties(userDto, userEntity);

		WeatherDto weatherDto = geolocationService.getWeather(ip);
		Weather weatherEntity = Weather.builder().build();
		BeanUtils.copyProperties(weatherDto, weatherEntity);

		userEntity.setWeather(weatherEntity);
		userRepository.save(userEntity);
	}

	@Override
	public void updateUser(Integer id, UserDto userDto) {
		User userEntity = userRepository.findById(id)
				.orElseThrow(
						() -> new RuntimeException("Usuário não encontrado")
				);

		BeanUtils.copyProperties(userDto, userEntity, "id");
		userRepository.save(userEntity);
	}

	@Override
	public void delete(Integer id) {
		userRepository.deleteById(id);
	}

	@Override
	public List<UserWeatherDto> getAllUsers() {
		return userRepository.findAll()
				.stream()
				.map(this::convertEntityToDto)
				.collect(Collectors.toList());
	}

	private UserWeatherDto convertEntityToDto(User userEntity) {
		UserWeatherDto userDto = new UserWeatherDto();
		BeanUtils.copyProperties(userEntity, userDto);
		this.setWeather(userEntity, userDto);
		return userDto;
	}

	private void setWeather(User userEntity, UserWeatherDto userDto) {
		if (Objects.nonNull(userEntity.getWeather())) {
			WeatherDto weatherDto = WeatherDto.builder().build();
			BeanUtils.copyProperties(userEntity.getWeather(), weatherDto);
			userDto.setWeather(weatherDto);
		}
	}
}
