package ec.com.danylassosolution.authentication.services.models.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ec.com.danylassosolution.authentication.exceptions.NotFoundException;
import ec.com.danylassosolution.authentication.models.constants.AppErrorsCodesAndMessages;
import ec.com.danylassosolution.authentication.models.dtos.ApiError;
import ec.com.danylassosolution.authentication.models.dtos.ApiValidationError;
import ec.com.danylassosolution.authentication.models.entities.UserApp;
import ec.com.danylassosolution.authentication.repositories.UserAppRepository;
import ec.com.danylassosolution.authentication.services.models.FindUserAppByNickname;
import ec.com.danylassosolution.authentication.util.LoggerCustom;

@Service
@Primary
public class FindUserAppByNicknameImp implements FindUserAppByNickname {
	
	@Autowired
	private UserAppRepository userAppRepository; 
	
	@Autowired
	private LoggerCustom logger;

	@Override
	public UserApp find(String nickname) {
		
		return	userAppRepository.findByNickname(nickname).orElseThrow(() -> {
			ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, AppErrorsCodesAndMessages.USER_NOT_FOUND.getMessage());
			
			ApiValidationError subError = ApiValidationError.builder()
															.code(AppErrorsCodesAndMessages.USER_NOT_FOUND.getCode())
															.object("User")
															.message(AppErrorsCodesAndMessages.USER_NOT_FOUND.getMessage().concat(" by nickname: ").concat(nickname))
															.field("nickname")
															.rejectedValue(nickname)
															.build();
			apiError.addSubError(subError);
			
			logger.error(apiError, getClass(), "find");
			
			return new NotFoundException(apiError);
		});
		
	}

}
