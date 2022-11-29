package ec.com.danylassosolution.users.services.models;

import ec.com.danylassosolution.users.models.dtos.EmployeeUpdateReq;
import ec.com.danylassosolution.users.models.dtos.UserUpdateReq;
import ec.com.danylassosolution.users.models.entities.User;

/**
 * 
 * @author Xaviervp
 *
 */
public interface UpdateUser {

	/**
	 * 
	 * @param request
	 * @return
	 */
	public User update(EmployeeUpdateReq request);
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	public User update(UserUpdateReq request);
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	public User update(User user);
}
