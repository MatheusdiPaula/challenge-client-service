package challenge.uol.service;

import challenge.uol.model.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class GeolocationServiceImpl implements GeolocationService {

	private static final String URL_IP = "https://ipvigilante.com/";
	private static final String URL_WEATHER = "https://www.metaweather.com/api/location/";
	private static final String SEARCH_BY_LAT_LONG = "search/?lattlong=";

	@Autowired
	private RestTemplate restTemplate;
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public WeatherDto getWeather(String ip) {
		GeolocationDto dto = this.getLocationByIp(ip);
		Integer woeid = this.getWoeidWeatherLocation(dto);
		try {
			ResponseEntity<String> json = restTemplate.getForEntity(URL_WEATHER + "/" + woeid, String.class);
			ResponseWeatherDto response = objectMapper.readValue(json.getBody(), ResponseWeatherDto.class);
			return response.getWeather()[0];
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private GeolocationDto getLocationByIp(String ip) {
		ResponseEntity<String> response = restTemplate.getForEntity(URL_IP + ip, String.class);
		try {
			return objectMapper.readValue(response.getBody(), ResponseGeolocationDto.class).getData();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Integer getWoeidWeatherLocation(GeolocationDto dto) {
		try {
			String latLong = String.join(",", dto.getLatitude(), dto.getLongitude());
			ResponseEntity<String> json = restTemplate.getForEntity(URL_WEATHER + SEARCH_BY_LAT_LONG + latLong, String.class);
			WeatherLocationDto[] WeatherLocation = objectMapper.readValue(json.getBody(), WeatherLocationDto[].class);
			return WeatherLocation[0].getWoeid();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
