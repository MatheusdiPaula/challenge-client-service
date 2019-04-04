package challenge.uol.service;

import challenge.uol.model.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.template.model.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class GeolocationService {

	private static final String URL_IP = "https://ipvigilante.com/";
	private static final String URL_WHEATHER = "https://www.metaweather.com/api/location/";
	private static final String SEARCH_BY_LAT_LONG = "search/?lattlong=";

	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;

	public GeolocationService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
		this.objectMapper = new ObjectMapper();
	}

	public WeatherDto getWheather(String ip) {
		GeolocationDto dto = this.getLocationByIp(ip);
		Integer woeid = this.getWoeidWheatherLocation(dto);
		try {
			String infoRequest = this.getInfoRequest(woeid);
			ResponseEntity<String> json = restTemplate.getForEntity(URL_WHEATHER + infoRequest, String.class);
			ResponseWeatherDto response = objectMapper.readValue(json.getBody(), ResponseWeatherDto.class);
			return response.getWheather()[0];
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

	private Integer getWoeidWheatherLocation(GeolocationDto dto) {
		try {
			String latLong = String.join(",", dto.getLatitude(), dto.getLongitude());
			ResponseEntity<String> json = restTemplate.getForEntity(URL_WHEATHER + SEARCH_BY_LAT_LONG + latLong, String.class);
			WeatherLocationDto[] wheatherLocation = objectMapper.readValue(json.getBody(), WeatherLocationDto[].class);
			return wheatherLocation[0].getWoeid();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String getInfoRequest(Integer woeid) {
		return String.join("/",
				woeid.toString());
	}

}
