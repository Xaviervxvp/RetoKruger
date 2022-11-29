package ec.com.danylassosolution.authentication.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;

import ec.com.danylassosolution.authentication.models.constants.SelfClaim;
import ec.com.danylassosolution.authentication.models.entities.UserApp;
import ec.com.danylassosolution.authentication.services.models.FindUserAppByNickname;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {
	
	private final JwtConfig jwtConfig;
    private JwtTokenProvider tokenProvider;
    private String rootNickname;
    private FindUserAppByNickname findUserAppByNickname; 



	public JwtTokenAuthenticationFilter(JwtConfig jwtConfig, JwtTokenProvider tokenProvider, String rootNickname,
			FindUserAppByNickname findUserAppByNickname) {
		super();
		this.jwtConfig = jwtConfig;
		this.tokenProvider = tokenProvider;
		this.rootNickname = rootNickname;
		this.findUserAppByNickname = findUserAppByNickname;
	}



	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		log.info("Authentication Request For '{}'", request.getRequestURL());
        
        String header = request.getHeader(jwtConfig.getHeader());
        
        if (header == null || !header.startsWith(jwtConfig.getPrefix())) {
        	filterChain.doFilter(request, response); 
            return;
        }
        
        String token = header.replace(jwtConfig.getPrefix(), "");
		
        token = token.trim();
        
        if(tokenProvider.validateToken(token)) {
        	System.out.println("token valido");
        	 Claims claims = tokenProvider.getClaimsFromJWT(token);
             String username = claims.getSubject();
             
             UsernamePasswordAuthenticationToken auth = null;
             
             if(rootNickname.equals(username)) {
            	 List<String> authorities = (List<String>) claims.get(SelfClaim.rol.toString());
            	 auth = new UsernamePasswordAuthenticationToken(username, null,  authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
             }else {
            	 UserApp userApp =  findUserAppByNickname.find(username);
            	 List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
         		for(String rol : userApp.getRol().split(",")) {
         			authorities.add(new SimpleGrantedAuthority(rol));
         		}
         		
         		User userSpringCore = new User(username, userApp.getPassword(), 
         								userApp.getUserEnable(), userApp.getAccountNonExpired(), userApp.getCredentialsNonExpired(), userApp.getAccountNonLocked(), 
         								authorities);
         		
            	 auth = new UsernamePasswordAuthenticationToken(userSpringCore, null, userSpringCore.getAuthorities());
             }
             
             SecurityContextHolder.getContext().setAuthentication(auth);

        }else {
        	 SecurityContextHolder.clearContext();
        }
        
        filterChain.doFilter(request, response);
	}
	
	

}
