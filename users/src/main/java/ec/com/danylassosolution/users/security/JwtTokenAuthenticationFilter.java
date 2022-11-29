package ec.com.danylassosolution.users.security;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import ec.com.danylassosolution.users.models.constants.SelfClaim;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {
	
	private final JwtConfig jwtConfig;
    private JwtTokenProvider tokenProvider;



	public JwtTokenAuthenticationFilter(JwtConfig jwtConfig, JwtTokenProvider tokenProvider) {
		super();
		this.jwtConfig = jwtConfig;
		this.tokenProvider = tokenProvider;
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
        	
        	 Claims claims = tokenProvider.getClaimsFromJWT(token);
             String username = claims.getSubject();
             
             UsernamePasswordAuthenticationToken auth = null;
             
             List<String> authorities = (List<String>) claims.get(SelfClaim.rol.toString());
        	 auth = new UsernamePasswordAuthenticationToken(username, null,  authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
             
             SecurityContextHolder.getContext().setAuthentication(auth);

        }else {
        	 SecurityContextHolder.clearContext();
        }
        
        filterChain.doFilter(request, response);
	}
	
	

}
