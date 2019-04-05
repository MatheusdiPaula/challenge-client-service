package challenge.uol.service;

import challenge.uol.model.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@RunWith(SpringRunner.class)
public class GeolocationServiceImplIntegrationTest {

	private static final String URL_IP = "https://ipvigilante.com/";
	private static final String URL_WEATHER = "https://www.metaweather.com/api/location/";
	private static final String SEARCH_BY_LAT_LONG = "search/?lattlong=";
	private static final Integer WOEID = 1;
	private static final String IP = "8.8.8.8";
	private static final String LAT_LONG = "-12,-30";
	private static final String LAT = "-12";
	private static final String LONG = "-30";
	private static final Long MIN_TEMP = Long.valueOf("10");
	private static final Long MAX_TEMP = Long.valueOf("20");

	private final ObjectMapper objectMapper = new ObjectMapper();

	@TestConfiguration
	static class GeolocationServiceImplTestContextConfiguration {
		@Bean
		public GeolocationService geolocationService() {
			return new GeolocationServiceImpl();
		}
	}

	@Autowired
	private GeolocationService geolocationService;
	@MockBean
	private RestTemplate restTemplate;

	@Before
	public void setUp() {
		Mockito.when(restTemplate.getForEntity(URL_WEATHER + "/" + WOEID, String.class))
				.thenReturn(this.getResponseWeatherDto());
		Mockito.when(restTemplate.getForEntity(URL_IP + IP, String.class))
				.thenReturn(this.getResponseGeolocationDto());
		Mockito.when(restTemplate.getForEntity(URL_WEATHER + SEARCH_BY_LAT_LONG + LAT_LONG, String.class))
				.thenReturn(this.getWeatherLocationDto());
	}

	@Test
	public void testGetWeather() {
		WeatherDto weather = geolocationService.getWeather(IP);
		Assert.assertEquals(MAX_TEMP, weather.getMaxTemp());
		Assert.assertEquals(MIN_TEMP, weather.getMinTemp());

	}

	private ResponseEntity<String> getResponseWeatherDto() {
		WeatherDto[] weatherDtos = new WeatherDto[1];
		weatherDtos[0] = WeatherDto.builder()
				.minTemp(MIN_TEMP)
				.maxTemp(MAX_TEMP)
				.build();
		return ResponseEntity.ok(this.getJson(
				ResponseWeatherDto.builder()
						.weather(weatherDtos)
						.build()
				)
		);
	}

	private ResponseEntity<String> getResponseGeolocationDto() {
		return ResponseEntity.ok(this.getJson(
				ResponseGeolocationDto.builder()
						.data(GeolocationDto.builder()
								.latitude(LAT)
								.longitude(LONG)
								.build()
						)
						.build()
				)
		);

	}

	private ResponseEntity<String> getWeatherLocationDto() {
		return ResponseEntity.ok(this.getJson(
				Collections.singletonList(WeatherLocationDto.builder()
						.woeid(WOEID)
						.build())
		));
	}

	private <T> String getJson(T t) {
		try {
			return objectMapper.writeValueAsString(t);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
