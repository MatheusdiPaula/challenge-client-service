package challenge.uol.model.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Weather {

	@Column(name = "min_temp")
	private Long minTemp;
	@Column(name = "max_temp")
	private Long maxTemp;
}
