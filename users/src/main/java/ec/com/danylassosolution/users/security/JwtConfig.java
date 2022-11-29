package ec.com.danylassosolution.users.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class JwtConfig {
	
    @Value("${config.security.jwt.header:Authorization}")
    private String header;

    @Value("${config.security.jwt.prefix:Bearer }")
    private String prefix;

    @Value("${config.security.jwt.expiration:#{24*60*60}}")
    private int expiration;

    @Value("${config.security.jwt.secret:JwtSecretKey}")
    private String secret;
}
