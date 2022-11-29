package ec.com.danylassosolution.users.services.models.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ec.com.danylassosolution.users.exceptions.DataException;
import ec.com.danylassosolution.users.models.constants.AppErrorsCodesAndMessages;
import ec.com.danylassosolution.users.models.dtos.ApiError;
import ec.com.danylassosolution.users.models.dtos.ApiValidationError;
import ec.com.danylassosolution.users.models.entities.Address;
import ec.com.danylassosolution.users.models.entities.User;
import ec.com.danylassosolution.users.repositories.AddressRepository;
import ec.com.danylassosolution.users.services.models.SaveAllAdress;
import ec.com.danylassosolution.users.utils.LoggerCustom;

@Service
@Primary
public class SaveAllAdressImp implements SaveAllAdress{
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private LoggerCustom logger; 

	@Override
	public List<Address> saveAll(List<Address> address, User user) {
		
		for(Address add : address) {
			add.setUser(user);
		}
		try {
			return addressRepository.saveAll(address);
		} catch (DataAccessException e) {
			ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorsCodesAndMessages.ADRESS_SAVE_ERROR.getMessage());
			
			ApiValidationError subError = ApiValidationError.builder()
															.code(AppErrorsCodesAndMessages.ADRESS_SAVE_ERROR.getCode())
															.object("address")
															.message(e.getMessage())
															.rejectedValue(address)
															.build();
			apiError.addSubError(subError);
			
			logger.error(apiError, getClass(), "saveAll", e);
			
			throw new DataException(apiError);
		}
	}

}
