package ec.com.danylassosolution.authentication.services;

import ec.com.danylassosolution.authentication.models.dtos.SignupEmployeeReq;
import ec.com.danylassosolution.authentication.models.dtos.SignupEmployeeResp;

public interface SignupEmployeesServices {

	/**
	 * 
	 * @param request
	 * @return
	 */
	public SignupEmployeeResp signupEmployee(SignupEmployeeReq request);
	
}
