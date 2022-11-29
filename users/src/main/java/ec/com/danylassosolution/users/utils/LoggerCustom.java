package ec.com.danylassosolution.users.utils;

import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import ec.com.danylassosolution.users.models.dtos.ApiError;
import ec.com.danylassosolution.users.models.dtos.ApiSubError;
import ec.com.danylassosolution.users.models.dtos.ApiValidationError;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoggerCustom {
	
	public void info(String message) {
		log.info(message);
	}

	public void error(ApiError apiError, Class<?> classFeacture, String method, Throwable ex) {
		log.error("[ERROR]\nDEBUG INFO: {}, Method: {}, Exception: {}\n[INFO RETURNED]: {}", classFeacture.getName(),
				method, ex.getMessage(), this.getInfoApiError(apiError));
	}

	public void error(ApiError apiError, Class<?> classFeacture, String method) {
		log.error("[ERROR]\nDEBUG INFO: {}, Method: {}\n[INFO RETURNED]: {}", classFeacture.getName(),
				method, this.getInfoApiError(apiError));
	}
	
	public void warn(Class<?> classFeacture, String method, String message) {
		log.warn("[{}]: {}\nMessage: {}", classFeacture.getName(), method, message);
	}

	private String getInfoApiError(ApiError apiError) {
		StringBuilder info = new StringBuilder("API ERROR INFO:\n");

		if (apiError.getStatus() != null) {
			info.append("HttpStatus:  " + apiError.getStatus().getReasonPhrase());
		}

		if (apiError.getMessage() != null) {
			info.append("\nMessage:  " + apiError.getMessage());
		}

		if (apiError.getTimestamp() != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

			String formatDateTime = apiError.getTimestamp().format(formatter);

			info.append("\nTimestamp:  " + formatDateTime);
		}

		if (apiError.getSubErrors() != null) {
			ApiValidationError apiValidationError;

			for (ApiSubError subError : apiError.getSubErrors()) {

				if (subError instanceof ApiValidationError) {
					apiValidationError = (ApiValidationError) subError;
					info.append(apiValidationError.toString());

				}
			}

		}

		return info.toString();
	}
}
