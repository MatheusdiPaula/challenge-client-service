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
public class WeatherLocationDto {

	@JsonProperty("distance")
	private Double distance;
	@JsonProperty("title")
	private String title;
	@JsonProperty("location_type")
	private String locationType;
	@JsonProperty("woeid")
	private Integer woeid;
	@JsonProperty("latt_long")
	private String latLong;

}
