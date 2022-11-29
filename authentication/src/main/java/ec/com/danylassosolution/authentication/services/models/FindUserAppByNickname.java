package ec.com.danylassosolution.authentication.services.models;

import ec.com.danylassosolution.authentication.models.entities.UserApp;

public interface FindUserAppByNickname {

	/**
	 * 
	 * @param nickname
	 * @return
	 */
	public UserApp find(String nickname);
}
