package ec.com.danylassosolution.authentication.exceptions;

import ec.com.danylassosolution.authentication.models.dtos.ApiError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Excepcion lanzada en los errores al acceder o enviar  los datos a la base
 * @author Xaviervp
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DataException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private ApiError apiError;

}
