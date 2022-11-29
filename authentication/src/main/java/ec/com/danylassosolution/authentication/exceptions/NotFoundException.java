package ec.com.danylassosolution.authentication.exceptions;

import ec.com.danylassosolution.authentication.models.dtos.ApiError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Excepciones lanzadas en ejecución al no encontrar los datos solicitados
 * @author Xaviervp
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ApiError apiError;
}
