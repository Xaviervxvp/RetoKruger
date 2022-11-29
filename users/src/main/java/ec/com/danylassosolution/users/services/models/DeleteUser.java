package ec.com.danylassosolution.users.services.models;

import ec.com.danylassosolution.users.models.entities.User;

public interface DeleteUser {

	public void delete(Long id);
	
	public void delete(String numDocument);
	
	public void delete(User user);
}
