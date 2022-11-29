package ec.com.danylassosolution.users.services.models.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ec.com.danylassosolution.users.exceptions.BusinessRulesException;
import ec.com.danylassosolution.users.exceptions.NotFoundException;
import ec.com.danylassosolution.users.models.constants.AppErrorsCodesAndMessages;
import ec.com.danylassosolution.users.models.dtos.ApiError;
import ec.com.danylassosolution.users.models.dtos.ApiValidationError;
import ec.com.danylassosolution.users.models.entities.User;
import ec.com.danylassosolution.users.repositories.UserRepository;
import ec.com.danylassosolution.users.services.models.FindUserByNumDocument;
import ec.com.danylassosolution.users.utils.LoggerCustom;

@Service
@Primary
public class FindUserByNumDocumentImp implements FindUserByNumDocument {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private LoggerCustom logger;
	
	@Override
	public User find(String numDocument) {
		List<User> users = userRepository.findByNumDocument(numDocument);
		
		if(users == null || users.isEmpty()) {
			ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, AppErrorsCodesAndMessages.USER_NOT_FOUND.getMessage());
			
			ApiValidationError subError = ApiValidationError.builder()
															.code(AppErrorsCodesAndMessages.USER_NOT_FOUND.getCode())
															.object("User")
															.message(AppErrorsCodesAndMessages.USER_NOT_FOUND.getMessage().concat(" by ci: ").concat(numDocument))
															.field("numDocument")
															.rejectedValue(numDocument)
															.build();
			apiError.addSubError(subError);
			
			logger.error(apiError, getClass(), "find");
			
			throw new NotFoundException(apiError);
		}
		
		if(users.size() != 1) {
			ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorsCodesAndMessages.USER_ILLEGAL_DATA.getMessage());
			
			ApiValidationError subError = ApiValidationError.builder()
															.code(AppErrorsCodesAndMessages.USER_ILLEGAL_DATA.getCode())
															.object("User")
															.message(AppErrorsCodesAndMessages.USER_ILLEGAL_DATA.getMessage())
															.build();
			apiError.addSubError(subError);
			
			logger.error(apiError, getClass(), "find -> exist many user with equal num document (CRITIAL ERROR)");
			
			throw new BusinessRulesException(apiError);
		}
		
		return users.get(0);
	}

}
