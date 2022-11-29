package ec.com.danylassosolution.authentication.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Xaviervp
 * @version 1.0.0
 * @apiNote Componente inyectado en SecurityConfig, en el método configure(HttpSecurity http)
 */
@Component
@Slf4j
public class UnauthorizedHandler implements AuthenticationEntryPoint {
	/**
	 * Este método es llamado cuando una excepción es lanzada porque un usuario
	 * no autenticado intenta acceder a un recurso que requiere autenticación.
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		log.error("[UNAUTHORIZED] user trying to access a resource that requires authentication - {}", authException.getMessage());
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
	}

}
