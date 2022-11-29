package ec.com.danylassosolution.authentication.services.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import ec.com.danylassosolution.authentication.models.dtos.SignupEmployeeReq;
import ec.com.danylassosolution.authentication.models.dtos.SignupEmployeeResp;
import ec.com.danylassosolution.authentication.services.SignupEmployeesServices;
import ec.com.danylassosolution.authentication.services.models.SaveUserCredentials;
import ec.com.danylassosolution.authentication.webclients.WebClientUsers;

@Service
@Primary
public class SignupEmployeesServicesImp implements SignupEmployeesServices {
	
	@Autowired
	private WebClientUsers webClient;
	
	@Autowired
	private SaveUserCredentials saveUserCredentials;

	@Override
	public SignupEmployeeResp signupEmployee(SignupEmployeeReq request) {
		
		webClient.registerEmployee(request);
		SignupEmployeeResp userAppCredentials = saveUserCredentials.save();
		return userAppCredentials;
	}

}
