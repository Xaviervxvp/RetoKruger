package ec.com.danylassosolution.users.models.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import ec.com.danylassosolution.users.annotations.CI;
import ec.com.danylassosolution.users.models.entities.User;
import lombok.Data;

@Data
public class EmployeeRegisterReq {

	@NotEmpty(message = "CI is required")
	@Size(min = 10, max = 10, message = "CI must has 10 digits")
	@CI
	private String ci;
	
	@NotEmpty(message = "Names are required")
	@Size(min = 2, max = 200, message = "Names must have min 2 character to 200 character")
	@Pattern(regexp = "^(?![ .]+$)[a-zA-Z .]*$", message = "Names must have only letters")
	private String names;
	
	@NotEmpty(message = "Lastnames is required")
	@Size(min = 2, max = 200, message = "Lastnames must have min 3 character to 200 character")
	@Pattern(regexp = "^(?![ .]+$)[a-zA-Z .]*$", message = "Lastnames must have only letters")
	private String lastnames;
	
	@NotEmpty(message = "Email is required")
	@Size(min = 3, max = 200, message = "Email must have min 3 character to 200 character")
	@Email(message = "Email malformat")
	private String email;
	
	public User toUserEntity() {
		User user = new User();
		
		user.setNumDocument(ci);
		user.setNames(names);
		user.setLastnames(lastnames);
		user.setEmail(email);
		user.setRol("emp");
		
		return user;
	}
	
	@Override
	public String toString() {
		return String.format("[ci]: %s; [names]: %s; [lastnames]: %s; [email]: %s", ci, names, lastnames, email);
	}
}
