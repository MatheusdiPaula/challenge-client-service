package challenge.uol.service;

import challenge.uol.model.dto.WeatherDto;

public interface GeolocationService {

	WeatherDto getWeather(String ip);
}
