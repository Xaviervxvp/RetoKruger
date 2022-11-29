package ec.com.danylassosolution.authentication.models.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;



import ec.com.danylassosolution.authentication.models.constants.VaccinationState;
import lombok.Data;

@Data
public class EmployeRegisterResp {
	private Long id;
	
	private String numDocument;
	
	private String names;
	
	private String lastnames;
	
	private String email;
    
    private LocalDateTime createdDate;

    
    private LocalDateTime modifiedDate;
    
    
	private String rol;
    
    
	private LocalDate birthday;
    
    
	private String cellphone;
    
    
	private VaccinationState isVaccinated;
}
