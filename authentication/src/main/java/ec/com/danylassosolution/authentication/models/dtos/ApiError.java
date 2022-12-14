package ec.com.danylassosolution.authentication.models.dtos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;

import ec.com.danylassosolution.authentication.models.constants.AppErrorsCodesAndMessages;
import ec.com.danylassosolution.authentication.util.LowerCaseClassNameResolver;
import lombok.Data;

/**
 * Objeto estandar de respuestas cuando existen errores
 * @author Xaviervp
 *
 */
@Data
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.CUSTOM, property = "error", visible = true)
@JsonTypeIdResolver(LowerCaseClassNameResolver.class)
public class ApiError {
	private HttpStatus status;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	private String message;
	private List<ApiSubError> subErrors;
	
	public ApiError(HttpStatus _status, String _message) {
		this.status = _status;
		timestamp = LocalDateTime.now();
		this.message = _message;
		subErrors = new ArrayList<ApiSubError>();
	}
	
	public ApiError(HttpStatus _status) {
		this.status = _status;
		timestamp = LocalDateTime.now();
		subErrors = new ArrayList<ApiSubError>();
	}

		
	public void addSubError(ApiSubError subError) {
		if (subErrors == null) {
			subErrors = new ArrayList<ApiSubError>();
		}
		subErrors.add(subError);
	}
	
	public void addSubErrors(ApiSubError ...subError) {
		if (subErrors == null) {
			subErrors = new ArrayList<ApiSubError>();
		}
		subErrors.addAll(subErrors);
	}
	
	public void addSubErrors( List<ApiSubError> subErrors) {
		if (subErrors == null) {
			subErrors = new ArrayList<ApiSubError>();
		}
		subErrors.addAll(subErrors);
	}
	
	public void addValidationErrors(List<FieldError> fieldErrors) {
		fieldErrors.forEach(this::addValidationError);
	}
	
	private void addValidationError(String object, String field, Object rejectedValue, String message) {
		addSubError(new ApiValidationError(object, field, rejectedValue, message, AppErrorsCodesAndMessages.FIELD_VALIDATION.getCode()));
	}

	
	private void addValidationError(FieldError fieldError) {
		this.addValidationError(fieldError.getObjectName(), fieldError.getField(), fieldError.getRejectedValue(),
				fieldError.getDefaultMessage());
	}
	
	private void addValidationError(String object, String message) {
		addSubError(new ApiValidationError(object, message));
	}

	
	private void addValidationError(ObjectError objectError) {
		this.addValidationError(objectError.getObjectName(), objectError.getDefaultMessage());
	}

	public void addValidationError(List<ObjectError> globalErrors) {
		globalErrors.forEach(this::addValidationError);
	}

	/**
	 * Utility method for adding error of ConstraintViolation. Usually when
	 * a @Validated validation fails.
	 *
	 * @param cv the ConstraintViolation
	 */
	private void addValidationError(ConstraintViolation<?> cv) {
		this.addValidationError(cv.getRootBeanClass().getSimpleName(),
				((PathImpl) cv.getPropertyPath()).getLeafNode().asString(), cv.getInvalidValue(), cv.getMessage());
	}

	public void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
		constraintViolations.forEach(this::addValidationError);
	}

}
