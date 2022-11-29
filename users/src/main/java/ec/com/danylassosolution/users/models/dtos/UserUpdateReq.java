package ec.com.danylassosolution.users.models.dtos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import ec.com.danylassosolution.users.annotations.BirthdayValidationFullAge;
import ec.com.danylassosolution.users.annotations.CI;
import ec.com.danylassosolution.users.models.constants.VaccinationState;
import ec.com.danylassosolution.users.models.entities.Address;
import ec.com.danylassosolution.users.models.entities.User;
import ec.com.danylassosolution.users.models.entities.Vaccines;
import lombok.Data;

@Data
public class UserUpdateReq {
	@NotNull(message = "Id is required")
	@Min(value = 1, message = "Id invalid")
	private Long id;
	
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
	
	@NotNull(message = "birthday is required")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@PastOrPresent(message = "birthday is not a date in the past")
	@BirthdayValidationFullAge
	private LocalDate birthday;
	
	@NotNull(message = "address of employee is required")
	private Address address;
	
	@NotEmpty(message = "Cellphone si required")
	@Size(min = 10, max = 13, message = "Cellphone must have of 10  to 13 digits")
	@Pattern(regexp = "^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$", message = "phone number malformed")
	private String cellphone;
	
	@NotNull(message = "Vaccination State is required")
	private VaccinationState vaccinationState;
	
	private List<Vaccines> vaccines;
	
	public User toUserEntity(User user) {
		 
		user.setId(id);
		user.setNumDocument(ci);
		user.setNames(names);
		user.setLastnames(lastnames);
		user.setEmail(email);
		user.setBirthday(birthday);
		user.setCellphone(cellphone);
		user.setIsVaccinated(this.vaccinationState);
	
		return user;
	}
	
	public List<Address> addressAsList(){
		List<Address> ltsAddress =new ArrayList<Address>();
		ltsAddress.add(this.address);
		
		return ltsAddress;
	}
}
