package ec.com.danylassosolution.authentication.controllers.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.com.danylassosolution.authentication.models.dtos.SignupEmployeeReq;
import ec.com.danylassosolution.authentication.models.dtos.SignupEmployeeResp;
import ec.com.danylassosolution.authentication.services.SignupEmployeesServices;

@RestController
@RequestMapping("/v1.0.0/signup")
public class SignupController {

	@Autowired
	private SignupEmployeesServices signupEmployeesServices;
	
	@PostMapping("/employees")
	public SignupEmployeeResp signupEmployee(@RequestBody SignupEmployeeReq request) {
		return signupEmployeesServices.signupEmployee(request);
	}
}
