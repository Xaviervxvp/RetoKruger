package ec.com.danylassosolution.users.exceptions;

import ec.com.danylassosolution.users.models.dtos.ApiError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Excepciones lanzadas cuando se rompe con las reglas de negocio
 * @author Xaviervp
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BusinessRulesException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ApiError apiError;
	
	
}
