package ec.com.danylassosolution.authentication.services.imp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ec.com.danylassosolution.authentication.exceptions.AuthException;
import ec.com.danylassosolution.authentication.models.constants.AppErrorsCodesAndMessages;
import ec.com.danylassosolution.authentication.models.constants.Rols;
import ec.com.danylassosolution.authentication.models.dtos.ApiError;
import ec.com.danylassosolution.authentication.models.dtos.ApiValidationError;
import ec.com.danylassosolution.authentication.models.dtos.LoginUserReq;
import ec.com.danylassosolution.authentication.models.dtos.LoginUserResp;
import ec.com.danylassosolution.authentication.models.entities.UserApp;
import ec.com.danylassosolution.authentication.security.JwtConfig;
import ec.com.danylassosolution.authentication.security.JwtTokenProvider;
import ec.com.danylassosolution.authentication.services.LoginUserService;
import ec.com.danylassosolution.authentication.services.models.FindUserAppByNickname;
import ec.com.danylassosolution.authentication.util.LoggerCustom;

/**
 * 
 * @author Xaviervp
 *
 */
@Service
@Primary
public class LoginUserServiceImp implements LoginUserService {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private JwtConfig jwtConfig;

	@Autowired
	private LoggerCustom logger;
	
	@Value("${config.security.user-root.nickname}")
	private String rootNickname;

	@Autowired
	private FindUserAppByNickname findUserAppByNickname;
	
	@Override
	public LoginUserResp login(LoginUserReq request) {
		try {
			Authentication authentication =authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsernameOrEmail(), request.getPassword()) );
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			
			Map<String, Object> claims;
			
			if(request.getUsernameOrEmail().equals(rootNickname)) {
				claims = this.buildClaimsRootUser();
			}else {
				UserApp user = findUserAppByNickname.find(request.getUsernameOrEmail());
				claims = this.buildClaims(user);	
			}
			
			String jwt = jwtTokenProvider.generateJwt(authentication, claims);
			return new LoginUserResp(jwt, jwtConfig.getPrefix());	
			
		} catch (BadCredentialsException e) {
			ApiError apiError =  this.buildApiErrorFailedAuth(e);
			throw new AuthException(apiError);
		} catch (LockedException e) {
			ApiError apiError =  this.buildApiErrorFailedAuth(e);
			throw new AuthException(apiError);
		} catch (DisabledException e) {
			ApiError apiError =  this.buildApiErrorFailedAuth(e);
			throw new AuthException(apiError);
		} catch (AccountExpiredException e) {
			ApiError apiError =  this.buildApiErrorFailedAuth(e);
			throw new AuthException(apiError);
		} catch (CredentialsExpiredException  e) {
			ApiError apiError =  this.buildApiErrorFailedAuth(e);
			throw new AuthException(apiError);
		}
		
	}
	
	private Map<String, Object> buildClaimsRootUser(){
		Map<String, Object> claims =  new HashMap<String, Object>();
		
		claims.put("rol",  Collections.singletonList("ROLE_" + Rols.USER_ROOT.toString()));
		
		return claims;
	}
	
	private Map<String, Object> buildClaims(UserApp user){
		Map<String, Object> claims =  new HashMap<String, Object>();
		
		List<String> roles = new ArrayList<String>();
		
		for(String rol: user.getRol().split(",")) {
			roles.add("ROLE_".concat(rol));
		}
		
		claims.put("rol", roles.toArray() );
		
		return claims;
	}
	
	private ApiError buildApiErrorFailedAuth(Throwable ex) {
		ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, AppErrorsCodesAndMessages.AUTH_FAILED.getMessage());
		
		ApiValidationError subError = ApiValidationError.builder()
														.code(AppErrorsCodesAndMessages.AUTH_FAILED.getCode())
														.message(AppErrorsCodesAndMessages.AUTH_FAILED.getMessage())
														.build();
		apiError.addSubError(subError);
		
		logger.error(apiError, getClass(), "login", ex);
		
		return apiError;
	}

}
