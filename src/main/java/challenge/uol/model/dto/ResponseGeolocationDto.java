package challenge.uol.model.dto;

import lombok.Data;

@Data
public class ResponseGeolocationDto {

	private String status;
	private GeolocationDto data;

}
