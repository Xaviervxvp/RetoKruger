package ec.com.danylassosolution.authentication.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ec.com.danylassosolution.authentication.models.entities.UserApp;
import ec.com.danylassosolution.authentication.services.models.FindUserAppByNickname;

@Service
public class AppUserDetailsService implements UserDetailsService {
	
	@Autowired
	private FindUserAppByNickname findUserAppByNickname;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserApp userApp = findUserAppByNickname.find(username);
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for(String rol : userApp.getRol().split(",")) {
			authorities.add(new SimpleGrantedAuthority(rol));
		}
		
		User userSpringCore = new User(username, userApp.getPassword(), 
								userApp.getUserEnable(), userApp.getAccountNonExpired(), userApp.getCredentialsNonExpired(), userApp.getAccountNonLocked(), 
								authorities);
		
		return userSpringCore;
	}

}
