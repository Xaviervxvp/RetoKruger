package ec.com.danylassosolution.users.services.models.imp;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ec.com.danylassosolution.users.exceptions.BusinessRulesException;
import ec.com.danylassosolution.users.exceptions.DataException;
import ec.com.danylassosolution.users.exceptions.NotFoundException;
import ec.com.danylassosolution.users.models.constants.AppErrorsCodesAndMessages;
import ec.com.danylassosolution.users.models.dtos.ApiError;
import ec.com.danylassosolution.users.models.dtos.ApiValidationError;
import ec.com.danylassosolution.users.models.dtos.EmployeeRegisterReq;
import ec.com.danylassosolution.users.models.entities.User;
import ec.com.danylassosolution.users.repositories.UserRepository;
import ec.com.danylassosolution.users.services.models.FindUserByNumDocument;
import ec.com.danylassosolution.users.services.models.SaveEmployee;
import ec.com.danylassosolution.users.utils.LoggerCustom;

@Service
@Primary
public class SaveEmployeeImp implements SaveEmployee {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private FindUserByNumDocument findUserByNumDocument;
	
	@Autowired
	private LoggerCustom logger;

	@Override
	public User create(EmployeeRegisterReq request) {
		
		this.checkCiUnique(request.getCi());
		
		try {
			User user = request.toUserEntity();
			return userRepository.save(user);
		} catch (DataAccessException e) {
			ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorsCodesAndMessages.USER_SAVE_ERROR.getMessage());
			
			ApiValidationError subError = ApiValidationError.builder()
															.code(AppErrorsCodesAndMessages.USER_SAVE_ERROR.getCode())
															.object(request.toString())
															.message(e.getMessage())
															.rejectedValue(request)
															.build();
			apiError.addSubError(subError);
			
			logger.error(apiError, getClass(), "create", e);
			
			throw new DataException(apiError);
		} 
	}
	
	private void checkCiUnique(String ci) {
		try {
			findUserByNumDocument.find(ci);
			
			ApiError apiError = new ApiError(HttpStatus.CONFLICT, AppErrorsCodesAndMessages.USER_NUM_DOCUMENT_ALREADY_USED.getMessage());
			
			ApiValidationError subError = ApiValidationError.builder()
															.code(AppErrorsCodesAndMessages.USER_NUM_DOCUMENT_ALREADY_USED.getCode())
															.object("User")
															.message(AppErrorsCodesAndMessages.USER_NUM_DOCUMENT_ALREADY_USED.getMessage())
															.rejectedValue(ci)
															.field("ci")
															.build();
			apiError.addSubError(subError);
			
			logger.error(apiError, getClass(), "checkCiUnique");
			
			throw new BusinessRulesException(apiError);
		} catch (NotFoundException e) {
			logger.info("validation if Ci is Unique successful");
		} catch (BusinessRulesException e) {
			throw e;
		}
	}

}
