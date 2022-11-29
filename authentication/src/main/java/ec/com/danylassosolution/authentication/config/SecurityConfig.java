package ec.com.danylassosolution.authentication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ec.com.danylassosolution.authentication.models.constants.Rols;
import ec.com.danylassosolution.authentication.security.AppUserDetailsService;
import ec.com.danylassosolution.authentication.security.JwtConfig;
import ec.com.danylassosolution.authentication.security.JwtTokenAuthenticationFilter;
import ec.com.danylassosolution.authentication.security.JwtTokenProvider;
import ec.com.danylassosolution.authentication.security.UnauthorizedHandler;
import ec.com.danylassosolution.authentication.services.models.FindUserAppByNickname;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AppUserDetailsService appUserDetailsService; 

	@Autowired
	private JwtConfig jwtConfig;
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private UnauthorizedHandler unauthorizedHandler;
	
	@Autowired
	private FindUserAppByNickname findUserAppByNickname;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Value("${config.security.user-root.nickname}")
	private String rootNickname;

	@Value("${config.security.user-root.password}")
	private String rootPassword;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		// Para acceso como super usuario (credenciales que no son de DB)
		auth.inMemoryAuthentication().withUser(rootNickname).password(passwordEncoder().encode(rootPassword))
				.roles(Rols.USER_ROOT.toString());
		
		//Para Empleados y Administradores
		auth.userDetailsService(this.appUserDetailsService).passwordEncoder(this.passwordEncoder());

	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/v2/api-docs", "/v3/api-docs/**", "/configuration/ui", "/swagger-resources/**",
				"/configuration/security", "/swagger-ui.html", "/swagger-ui/**", "/webjars/**" , "/h2-console/**");
	}
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
            .and()
            .addFilterBefore(new JwtTokenAuthenticationFilter(jwtConfig, tokenProvider, rootNickname, findUserAppByNickname), 
            		UsernamePasswordAuthenticationFilter.class)
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, "/v1.0.0/auth/login").permitAll() //Login
            .anyRequest().hasAnyRole(Rols.USER_ROOT.toString())
            ; 
              
    }
}
