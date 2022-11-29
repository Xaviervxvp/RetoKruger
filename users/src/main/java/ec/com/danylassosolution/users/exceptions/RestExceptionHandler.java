package ec.com.danylassosolution.users.exceptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import ec.com.danylassosolution.users.models.dtos.ApiError;
import lombok.extern.slf4j.Slf4j;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler{

	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
	
	/**
	 * Handle MissingServletRequestParameterException. Triggered when a 'required'
	 * request parameter is missing.
	 *
	 * @param ex      MissingServletRequestParameterException
	 * @param headers HttpHeaders
	 * @param status  HttpStatus
	 * @param request WebRequest
	 * @return the ApiError object
	 */
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = ex.getParameterName() + " parameter is missing \nException message: " + ex.getMessage();
		log.error(error, ex);
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error));
	}

	/**
	 * Handle HttpMediaTypeNotSupportedException. This one triggers when JSON is
	 * invalid as well.
	 *
	 * @param ex      HttpMediaTypeNotSupportedException
	 * @param headers HttpHeaders
	 * @param status  HttpStatus
	 * @param request WebRequest
	 * @return the ApiError object
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		StringBuilder builder = new StringBuilder();
		builder.append(ex.getContentType());
		builder.append(" media type is not supported. Supported media types are ");
		ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
		
		log.error(builder.toString(), ex);
		return buildResponseEntity(
				new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, builder.substring(0, builder.length() - 2)));
	}

	/**
	 * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid
	 * validation.
	 *
	 * @param ex      the MethodArgumentNotValidException that is thrown when @Valid
	 *                validation fails
	 * @param headers HttpHeaders
	 * @param status  HttpStatus
	 * @param request WebRequest
	 * @return the ApiError object
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.PRECONDITION_REQUIRED);
		apiError.setMessage("Datos de error de validacion");
		apiError.addValidationErrors(ex.getBindingResult().getFieldErrors());
		apiError.addValidationError(ex.getBindingResult().getGlobalErrors());
		return buildResponseEntity(apiError);
	}

	/**
	 * Handles javax.validation.ConstraintViolationException. Thrown when @Validated
	 * fails.
	 *
	 * @param ex the ConstraintViolationException
	 * @return the ApiError object
	 */
	@ExceptionHandler(javax.validation.ConstraintViolationException.class)
	protected ResponseEntity<Object> handleConstraintViolation(javax.validation.ConstraintViolationException ex) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
		apiError.setMessage("Validation error");
		apiError.addValidationErrors(ex.getConstraintViolations());
		return buildResponseEntity(apiError);
	}

	/**
	 * Handle HttpMessageNotReadableException. Happens when request JSON is
	 * malformed.
	 *
	 * @param ex      HttpMessageNotReadableException
	 * @param headers HttpHeaders
	 * @param status  HttpStatus
	 * @param request WebRequest
	 * @return the ApiError object
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ServletWebRequest servletWebRequest = (ServletWebRequest) request;
		String error = "Malformed JSON request";
		log.error(error + "\n{} to {} \nexception: {}", servletWebRequest.getHttpMethod(), servletWebRequest.getRequest().getServletPath(), ex.getMessage());
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error));
	}

	/**
	 * Handle HttpMessageNotWritableException.
	 *
	 * @param ex      HttpMessageNotWritableException
	 * @param headers HttpHeaders
	 * @param status  HttpStatus
	 * @param request WebRequest
	 * @return the ApiError object
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = "Error writing JSON output";
		log.error(error, ex);
		return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, error));
	}

	/**
	 * Handle NoHandlerFoundException.
	 *
	 * @param ex
	 * @param headers
	 * @param status
	 * @param request
	 * @return
	 */
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
		apiError.setMessage(
				String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()));
		log.error(apiError.getMessage(), ex);
		return buildResponseEntity(apiError);
	}

	/**
	 * Handle javax.persistence.EntityNotFoundException
	 */
	@ExceptionHandler(javax.persistence.EntityNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFound(javax.persistence.EntityNotFoundException ex) {
		return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, ex.getMessage()));
	}


	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
		
		if(ex.getApiError() != null ) {
			apiError = ex.getApiError();
		}
		
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(BusinessRulesException.class)
	public ResponseEntity<Object> handleBusinessRulesException(BusinessRulesException ex) {
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
		
		if(ex.getApiError() != null ) {
			apiError = ex.getApiError();
		}
		
		return buildResponseEntity(apiError);
	}
	
	@ExceptionHandler(DataException.class)
	public ResponseEntity<Object> handleDataException(DataException ex) {
		ApiError apiError = new ApiError(HttpStatus.CONFLICT);
		
		if(ex.getApiError() != null ) {
			apiError = ex.getApiError();
		}
		
		return buildResponseEntity(apiError);
	}
		
}
