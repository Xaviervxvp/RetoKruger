package ec.com.danylassosolution.authentication.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Xaviervp
 *
 */
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class ApiValidationError extends ApiSubError {

	private String object;
	private String field;
	private Object rejectedValue;
	private String message;
	private String code;
	
	
	public ApiValidationError(String object, String message) {
		super();
		this.object = object;
		this.message = message;
	}
	
	@Override
	public String toString() {
		return String.format("\nobject: %s, field: %s, message: %s, code: %s \n", object, field, message, code);
	}

}
