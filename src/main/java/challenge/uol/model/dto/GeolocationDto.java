package challenge.uol.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeolocationDto {

	@JsonProperty("ipv4")
	private String ip;
	@JsonProperty("continent_name")
	private String continentName;
	@JsonProperty("country_name")
	private String countryName;
	@JsonProperty("subdivision_1_name")
	private String subdivisionName1;
	@JsonProperty("subdivision_2_name")
	private String subdivisionName2;
	@JsonProperty("city_name")
	private String cityName;
	@JsonProperty("latitude")
	private String latitude;
	@JsonProperty("longitude")
	private String longitude;
}
