package challenge.uol.factory;

import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Getter
public class DependencyFactory {

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

}
