package ec.com.danylassosolution.authentication.models.dtos;

import lombok.Data;

@Data
public class LoginUserReq {

	private String usernameOrEmail;
	private String password;
}
