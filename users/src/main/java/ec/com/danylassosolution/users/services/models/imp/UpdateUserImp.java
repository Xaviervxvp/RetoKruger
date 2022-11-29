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
import ec.com.danylassosolution.users.models.dtos.EmployeeUpdateReq;
import ec.com.danylassosolution.users.models.dtos.UserUpdateReq;
import ec.com.danylassosolution.users.models.entities.User;
import ec.com.danylassosolution.users.repositories.UserRepository;
import ec.com.danylassosolution.users.services.models.FindUserById;
import ec.com.danylassosolution.users.services.models.UpdateUser;
import ec.com.danylassosolution.users.utils.LoggerCustom;

@Service
@Primary
public class UpdateUserImp implements UpdateUser {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private FindUserById findUserById;
	

	@Autowired
	private LoggerCustom logger;

	@Override
	public User update(EmployeeUpdateReq request) {
		User user = findUserById.find(request.getId());
		
		user = request.toUserEntity(user);
		
		return this.update(user);
		
	}
	
	@Override
	public User update(UserUpdateReq request) {
		User user = findUserById.find(request.getId());
		
		user = request.toUserEntity(user);
		
		return this.update(user);
	}

	@Override
	public User update(User user) {
		try {
			return userRepository.save(user);
		} catch (DataAccessException e) {
			ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorsCodesAndMessages.USER_SAVE_ERROR.getMessage());
			
			ApiValidationError subError = ApiValidationError.builder()
															.code(AppErrorsCodesAndMessages.USER_SAVE_ERROR.getCode())
															.object("user")
															.message(e.getMessage())
															.rejectedValue(user)
															.build();
			apiError.addSubError(subError);
			
			logger.error(apiError, getClass(), "update", e);
			
			throw new DataException(apiError);
		}
	}

	

}
 