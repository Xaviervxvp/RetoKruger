package ec.com.danylassosolution.users.services.models.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ec.com.danylassosolution.users.exceptions.NotFoundException;
import ec.com.danylassosolution.users.models.constants.AppErrorsCodesAndMessages;
import ec.com.danylassosolution.users.models.dtos.ApiError;
import ec.com.danylassosolution.users.models.dtos.ApiValidationError;
import ec.com.danylassosolution.users.models.entities.User;
import ec.com.danylassosolution.users.repositories.UserRepository;
import ec.com.danylassosolution.users.services.models.FindUserById;
import ec.com.danylassosolution.users.utils.LoggerCustom;

@Service
@Primary
public class FindUserByIdImp implements FindUserById{


	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private LoggerCustom logger;
	
	@Override
	public User find(Long id) {
		return userRepository.findById(id).orElseThrow(() -> {
			ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, AppErrorsCodesAndMessages.USER_NOT_FOUND.getMessage());
			
			ApiValidationError subError = ApiValidationError.builder()
					.code(AppErrorsCodesAndMessages.USER_NOT_FOUND.getCode())
					.object("User")
					.message(AppErrorsCodesAndMessages.USER_NOT_FOUND.getMessage().concat(" by id: ").concat(id.toString()))
					.field("id")
					.rejectedValue(id)
					.build();
			apiError.addSubError(subError);
			
			logger.error(apiError, getClass(), "find");
			
			return  new NotFoundException(apiError);
		});
	}

}
