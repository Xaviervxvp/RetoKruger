package ec.com.danylassosolution.authentication.exceptions;

import ec.com.danylassosolution.authentication.models.dtos.ApiError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AuthException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ApiError apiError;

}
