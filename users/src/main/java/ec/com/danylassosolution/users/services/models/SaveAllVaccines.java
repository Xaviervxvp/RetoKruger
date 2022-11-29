package ec.com.danylassosolution.users.services.models;

import java.util.List;

import ec.com.danylassosolution.users.models.entities.User;
import ec.com.danylassosolution.users.models.entities.Vaccines;

public interface SaveAllVaccines {

	/**
	 * SaveAllVaccines
	 * @param vaccines
	 * @param user
	 * @return
	 */
	public List<Vaccines> saveAll(List<Vaccines> vaccines, User user);
}
