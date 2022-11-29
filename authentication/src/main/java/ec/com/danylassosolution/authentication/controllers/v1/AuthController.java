package ec.com.danylassosolution.authentication.controllers.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.com.danylassosolution.authentication.models.dtos.LoginUserReq;
import ec.com.danylassosolution.authentication.models.dtos.LoginUserResp;

import ec.com.danylassosolution.authentication.services.LoginUserService;

@RestController
@RequestMapping("/v1.0.0/auth")
public class AuthController {

	@Autowired
	private LoginUserService LoginUserService;
	
	@PostMapping("/login")
	public LoginUserResp login(@RequestBody LoginUserReq request) {
		return LoginUserService.login(request);
	}
	
}
