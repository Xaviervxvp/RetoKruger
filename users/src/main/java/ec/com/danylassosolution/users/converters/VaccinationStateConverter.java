package ec.com.danylassosolution.users.converters;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.springframework.http.HttpStatus;

import ec.com.danylassosolution.users.exceptions.BusinessRulesException;
import ec.com.danylassosolution.users.models.constants.AppErrorsCodesAndMessages;
import ec.com.danylassosolution.users.models.constants.VaccinationState;
import ec.com.danylassosolution.users.models.dtos.ApiError;
import ec.com.danylassosolution.users.models.dtos.ApiValidationError;

@Converter(autoApply = true)
public class VaccinationStateConverter implements AttributeConverter<VaccinationState, String> {

	@Override
	public String convertToDatabaseColumn(VaccinationState attribute) {
		if(attribute == null) {
			return null;
		}
		return attribute.getCode();
	}

	@Override
	public VaccinationState convertToEntityAttribute(String dbData) {
		if (dbData == null) {
            return null;
        }
		
		return Stream.of(VaccinationState.values())
		          .filter(c -> c.getCode().equals(dbData))
		          .findFirst()
		          .orElseThrow( ()  -> {
		        	ApiError apiError = new ApiError(HttpStatus.PRECONDITION_FAILED, AppErrorsCodesAndMessages.VACCINE_STATE_INVALID.getMessage());
		  			
		  			ApiValidationError subError = ApiValidationError.builder()
		  															.code(AppErrorsCodesAndMessages.VACCINE_STATE_INVALID.getCode())
		  															.object("User")
		  															.message(AppErrorsCodesAndMessages.VACCINE_STATE_INVALID.getMessage())
		  															.rejectedValue(dbData)
		  															.field("VaccinationState")
		  															.build();
		  			apiError.addSubError(subError);
		  			
		  			return new BusinessRulesException(apiError);
		          });
	}

}
