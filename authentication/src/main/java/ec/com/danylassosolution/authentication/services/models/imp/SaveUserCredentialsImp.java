package ec.com.danylassosolution.authentication.services.models.imp;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ec.com.danylassosolution.authentication.exceptions.DataException;
import ec.com.danylassosolution.authentication.models.constants.AppErrorsCodesAndMessages;
import ec.com.danylassosolution.authentication.models.constants.Rols;
import ec.com.danylassosolution.authentication.models.dtos.ApiError;
import ec.com.danylassosolution.authentication.models.dtos.ApiValidationError;
import ec.com.danylassosolution.authentication.models.dtos.SignupEmployeeResp;
import ec.com.danylassosolution.authentication.models.entities.UserApp;
import ec.com.danylassosolution.authentication.repositories.UserAppRepository;
import ec.com.danylassosolution.authentication.security.JwtTokenProvider;
import ec.com.danylassosolution.authentication.services.models.SaveUserCredentials;
import ec.com.danylassosolution.authentication.util.LoggerCustom;

@Service
@Primary
public class SaveUserCredentialsImp implements SaveUserCredentials {
	
	@Autowired
	private UserAppRepository userAppRepository;
	
	 @Autowired
	 private JwtTokenProvider jwtProvider;

	 @Autowired
	 private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private LoggerCustom logger;
	 
	@Override
	public SignupEmployeeResp save() {
		UserApp user = new UserApp();
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		user.setFailedAuthentications(0);
		user.setModifiedPassword(LocalDateTime.now());
		user.setRol(Rols.EMPLOYEE.toString());
		user.setUserEnable(true);
		
		String nickname =this.generateNickname(); 
		user.setNickname(nickname);
		
		String password = jwtProvider.generateJwtAsTempPassword();
		user.setPassword(passwordEncoder.encode(password));
		
		try {
			userAppRepository.save(user);
			return new SignupEmployeeResp(nickname, password);
		} catch (DataAccessException e) {
			ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorsCodesAndMessages.USER_SAVE_ERROR.getMessage());
			
			ApiValidationError subError = ApiValidationError.builder()
															.code(AppErrorsCodesAndMessages.USER_SAVE_ERROR.getCode())
															.object("user")
															.message(e.getMessage())
															.build();
			apiError.addSubError(subError);
			
			logger.error(apiError, getClass(), "create", e);
			
			throw new DataException(apiError);
		}
	}
	
	private String generateNickname() {
		return UUID.randomUUID().toString();
	}
	
	

}
