package ec.com.danylassosolution.users.services.models.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ec.com.danylassosolution.users.exceptions.DataException;
import ec.com.danylassosolution.users.models.constants.AppErrorsCodesAndMessages;
import ec.com.danylassosolution.users.models.dtos.ApiError;
import ec.com.danylassosolution.users.models.dtos.ApiValidationError;
import ec.com.danylassosolution.users.models.entities.User;
import ec.com.danylassosolution.users.repositories.UserRepository;
import ec.com.danylassosolution.users.services.models.DeleteUser;
import ec.com.danylassosolution.users.services.models.FindUserById;
import ec.com.danylassosolution.users.services.models.FindUserByNumDocument;
import ec.com.danylassosolution.users.utils.LoggerCustom;

@Service
@Primary
public class DeleteUserImp implements DeleteUser {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private FindUserById findUserById;
	
	@Autowired
	private FindUserByNumDocument findUserByNumDocument;

	@Autowired
	private LoggerCustom logger;

	@Override
	public void delete(Long id) {
		User user = findUserById.find(id);
		this.delete(user);
	}

	@Override
	public void delete(String numDocument) {
		User user = findUserByNumDocument.find(numDocument);
		this.delete(user);
		
	}

	@Override
	public void delete(User user) {
		try {
			userRepository.delete(user);
		} catch (DataAccessException e) {
			ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorsCodesAndMessages.USER_DELETE_ERROR.getMessage());
			
			ApiValidationError subError = ApiValidationError.builder()
															.code(AppErrorsCodesAndMessages.USER_DELETE_ERROR.getCode())
															.object("user")
															.message(e.getMessage())
															.rejectedValue(user)
															.build();
			apiError.addSubError(subError);
			
			logger.error(apiError, getClass(), "delete", e);
			
			throw new DataException(apiError);
		}
		
	}

}
