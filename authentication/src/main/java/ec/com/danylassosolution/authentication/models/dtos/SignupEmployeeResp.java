package ec.com.danylassosolution.authentication.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SignupEmployeeResp {
	
	private String username;
	
	private String passwordTemp;
		
}
