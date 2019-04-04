package challenge.uol.factory;

import challenge.uol.respository.UserRepository;
import challenge.uol.service.GeolocationService;
import challenge.uol.service.UserService;
import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Getter
public class DependencyFactory {

	private UserRepository userRepository;

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@Bean
	public UserService getUserService() {
		return new UserService(this.getUserRepository(), this.getGeolocationService());
	}

	@Bean
	public GeolocationService getGeolocationService() {
		return new GeolocationService(this.getRestTemplate());
	}
}
