package challenge.uol.service;

import challenge.uol.model.db.User;
import challenge.uol.model.db.Weather;
import challenge.uol.model.dto.UserDto;
import challenge.uol.model.dto.UserWeatherDto;
import challenge.uol.model.dto.WeatherDto;
import challenge.uol.respository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
public class UserServiceImplIntegrationTest {

	private static final Integer ID = 1;
	private static final Integer ID_NOT_FOUND = 2;
	private static final Long MIN_TEMP = Long.valueOf("20");
	private static final Long MAX_TEMP = Long.valueOf("30");
	private static final Integer AGE = 25;
	private static final String NAME = "Name";
	private static final String NAME_UPDATED = "Name Updated";
	private static final Integer VERSION = 1;
	private static final String IP = "8.8.8.8";
	private static final int SIZE_USERS = 1;

	@TestConfiguration
	static class UserServiceImplTestContextConfiguration {
		@Bean
		public UserService userService() {
			return new UserServiceImpl();
		}
	}

	@Autowired
	private UserService userService;
	@MockBean
	private UserRepository userRepository;
	@MockBean
	private GeolocationService geolocationService;

	@Before
	public void setUp() {
		Mockito.when(userRepository.findById(ID)).thenReturn(Optional.of(this.getUser()));
		Mockito.when(userRepository.findById(ID_NOT_FOUND)).thenReturn(Optional.empty());
		Mockito.when(userRepository.findAll()).thenReturn(Collections.singletonList(this.getUser()));
		Mockito.when(geolocationService.getWeather(IP)).thenReturn(this.getWeatherDto());
	}

	@Test
	public void testGetByIdSuccess() {
		UserWeatherDto userById = userService.getUserById(ID);
		Assert.assertEquals(ID, userById.getId());
		Assert.assertEquals(NAME, userById.getName());
		Assert.assertEquals(AGE, userById.getAge());
		Assert.assertEquals(MIN_TEMP, userById.getWeather().getMinTemp());
		Assert.assertEquals(MAX_TEMP, userById.getWeather().getMaxTemp());
	}

	@Test(expected = RuntimeException.class)
	public void testGetByIdNotFound() {
		userService.getUserById(ID_NOT_FOUND);
	}

	@Test
	public void testGetAllUsers() {
		List<UserWeatherDto> allUsers = userService.getAllUsers();
		Assert.assertEquals(SIZE_USERS, allUsers.size());

		UserWeatherDto result = allUsers.get(0);
		Assert.assertEquals(NAME, result.getName());
		Assert.assertEquals(AGE, result.getAge());
		Assert.assertEquals(MIN_TEMP, result.getWeather().getMinTemp());
		Assert.assertEquals(MAX_TEMP, result.getWeather().getMaxTemp());
	}

	@Test
	public void testCreateUser() {
		userService.createUser(this.getUserDto(), IP);
		ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
		Mockito.verify(userRepository).save(userArgumentCaptor.capture());

		User result = userArgumentCaptor.getValue();
		Assert.assertEquals(NAME, result.getName());
		Assert.assertEquals(AGE, result.getAge());
		Assert.assertEquals(MIN_TEMP, result.getWeather().getMinTemp());
		Assert.assertEquals(MAX_TEMP, result.getWeather().getMaxTemp());
	}

	@Test
	public void testUpdate() {
		UserDto userDto = this.getUserDto();
		userDto.setName(NAME_UPDATED);
		userService.updateUser(ID, userDto);
		ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
		Mockito.verify(userRepository).save(userArgumentCaptor.capture());

		User result = userArgumentCaptor.getValue();
		Assert.assertEquals(NAME_UPDATED, result.getName());
	}

	@Test(expected = RuntimeException.class)
	public void testUpdateUserNotFound() {
		userService.updateUser(ID_NOT_FOUND, this.getUserDto());
	}

	@Test
	public void testDelete() {
		userService.delete(ID);
		Mockito.verify(userRepository).deleteById(ID);
	}

	private WeatherDto getWeatherDto() {
		return WeatherDto.builder()
				.maxTemp(MAX_TEMP)
				.minTemp(MIN_TEMP)
				.build();
	}

	private Weather getWeather() {
		return Weather.builder()
				.maxTemp(MAX_TEMP)
				.minTemp(MIN_TEMP)
				.build();
	}

	private User getUser() {
		return User.builder()
				.id(ID)
				.age(AGE)
				.name(NAME)
				.weather(this.getWeather())
				.version(VERSION)
				.build();
	}

	private UserDto getUserDto() {
		return UserDto.builder()
				.name(NAME)
				.age(AGE)
				.build();
	}

}
