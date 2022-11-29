package ec.com.danylassosolution.users.services.models;

import java.util.List;

import ec.com.danylassosolution.users.models.entities.Address;
import ec.com.danylassosolution.users.models.entities.User;

public interface SaveAllAdress {

	/**
	 * registra todas las direcciones del usuario
	 * @param address
	 * @param user
	 * @return
	 */
	public List<Address> saveAll(List<Address> address, User user);
}
