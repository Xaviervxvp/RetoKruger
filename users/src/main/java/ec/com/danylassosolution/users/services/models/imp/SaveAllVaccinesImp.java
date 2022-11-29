package ec.com.danylassosolution.users.services.models.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ec.com.danylassosolution.users.exceptions.BusinessRulesException;
import ec.com.danylassosolution.users.exceptions.DataException;
import ec.com.danylassosolution.users.models.constants.AppErrorsCodesAndMessages;
import ec.com.danylassosolution.users.models.constants.VaccinationState;
import ec.com.danylassosolution.users.models.dtos.ApiError;
import ec.com.danylassosolution.users.models.dtos.ApiValidationError;
import ec.com.danylassosolution.users.models.entities.User;
import ec.com.danylassosolution.users.models.entities.Vaccines;
import ec.com.danylassosolution.users.repositories.VaccinesRepository;
import ec.com.danylassosolution.users.services.models.SaveAllVaccines;
import ec.com.danylassosolution.users.utils.LoggerCustom;

@Service
@Primary
public class SaveAllVaccinesImp implements SaveAllVaccines {
	
	@Autowired
	private VaccinesRepository vaccinesRepository; 

	@Autowired
	private LoggerCustom logger;

	@Override
	public List<Vaccines> saveAll(List<Vaccines> vaccines, User user) {
		
		this.checkIsVaccinated(vaccines, user);
		
		for(Vaccines vac : vaccines) {
			vac.setUser(user);
		}
		
		try {
			return vaccinesRepository.saveAll(vaccines);
		} catch (DataAccessException e) {
			ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorsCodesAndMessages.VACCINES_SAVE_ERROR.getMessage());
			
			ApiValidationError subError = ApiValidationError.builder()
															.code(AppErrorsCodesAndMessages.VACCINES_SAVE_ERROR.getCode())
															.object("vaccines")
															.message(e.getMessage())
															.rejectedValue(vaccines)
															.build();
			apiError.addSubError(subError);
			
			logger.error(apiError, getClass(), "saveAll", e);
			
			throw new DataException(apiError);
		}
	}
	
	private void checkIsVaccinated(List<Vaccines> vaccines, User user) {
		if(user.getIsVaccinated().equals(VaccinationState.VACCINATED) && (vaccines == null || vaccines.isEmpty()) ) {
			ApiError apiError = new ApiError(HttpStatus.PRECONDITION_FAILED, AppErrorsCodesAndMessages.VACCINE_TYPE_INVALID.getMessage());
			
			ApiValidationError subError = ApiValidationError.builder()
															.code(AppErrorsCodesAndMessages.VACCINE_TYPE_INVALID.getCode())
															.object("vaccines")
															.message(AppErrorsCodesAndMessages.VACCINE_TYPE_INVALID.getMessage())
															.rejectedValue(user.getIsVaccinated())
															.field("isVaccinated")
															.build();
			apiError.addSubError(subError);
			
			throw new BusinessRulesException(apiError);
		}
	}

}
