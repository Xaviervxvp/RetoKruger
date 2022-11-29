package ec.com.danylassosolution.users.services.models;

import ec.com.danylassosolution.users.models.dtos.EmployeeRegisterReq;
import ec.com.danylassosolution.users.models.entities.User;

public interface SaveEmployee {

	/**
	 * Registra los datos básicos de un empleado
	 * @param request
	 * @throws DataException - En caso de existir problemas con la comunicación a la base de datos
	 * @throws BusinessRulesException - número de documento repetido
	 * @return User created
	 */
	public User create(EmployeeRegisterReq request);
}
