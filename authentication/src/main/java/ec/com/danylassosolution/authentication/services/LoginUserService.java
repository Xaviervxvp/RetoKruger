package ec.com.danylassosolution.authentication.services;

import ec.com.danylassosolution.authentication.models.dtos.LoginUserReq;
import ec.com.danylassosolution.authentication.models.dtos.LoginUserResp;

public interface LoginUserService {

	/**
	 * 
	 * @param request
	 * @return
	 */
	public LoginUserResp login(LoginUserReq request);
}
