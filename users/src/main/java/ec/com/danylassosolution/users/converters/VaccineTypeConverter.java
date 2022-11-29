package ec.com.danylassosolution.users.converters;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.springframework.http.HttpStatus;

import ec.com.danylassosolution.users.exceptions.BusinessRulesException;
import ec.com.danylassosolution.users.models.constants.AppErrorsCodesAndMessages;
import ec.com.danylassosolution.users.models.constants.VaccineType;
import ec.com.danylassosolution.users.models.dtos.ApiError;
import ec.com.danylassosolution.users.models.dtos.ApiValidationError;

@Converter(autoApply = true)
public class VaccineTypeConverter implements AttributeConverter<VaccineType, String> {

	@Override
	public String convertToDatabaseColumn(VaccineType attribute) {
		if(attribute == null) {
			return null;
		}
		return attribute.getCode();
	}

	@Override
	public VaccineType convertToEntityAttribute(String dbData) {
		if (dbData == null) {
            return null;
        }
		
		return Stream.of(VaccineType.values())
		          .filter(c -> c.getCode().equals(dbData))
		          .findFirst()
		          .orElseThrow( ()  -> {
		        	ApiError apiError = new ApiError(HttpStatus.PRECONDITION_FAILED, AppErrorsCodesAndMessages.VACCINE_TYPE_INVALID.getMessage());
		  			
		  			ApiValidationError subError = ApiValidationError.builder()
		  															.code(AppErrorsCodesAndMessages.VACCINE_TYPE_INVALID.getCode())
		  															.object("User")
		  															.message(AppErrorsCodesAndMessages.VACCINE_TYPE_INVALID.getMessage())
		  															.rejectedValue(dbData)
		  															.field("VaccineType")
		  															.build();
		  			apiError.addSubError(subError);
		  			
		  			return  new BusinessRulesException(apiError);
		          });
	}

}
