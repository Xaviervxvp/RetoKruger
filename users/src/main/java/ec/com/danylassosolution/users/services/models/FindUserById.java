package ec.com.danylassosolution.users.services.models;

import ec.com.danylassosolution.users.models.entities.User;

public interface FindUserById {

	/**
	 * Encuentra un usuario según el id
	 * @param id
	 * @throws NotFoundException - en caso de no existir el usuario
	 * @return
	 */
	public User find(Long id);
}
