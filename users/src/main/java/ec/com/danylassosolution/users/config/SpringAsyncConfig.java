package ec.com.danylassosolution.users.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import ec.com.danylassosolution.users.exceptions.AsyncExceptionHandler;


/**
 * Habilita procesos asincrónos, es decir, al llamar 
 * métodos asincrónos el sistema no esperará a que se complete el método.
 * @author Xaviervp
 * @see https://www.baeldung.com/spring-async
 */
@Configuration
@EnableAsync
@ComponentScan("ec.com.danyLassoSolution.emailNotifier.senders.imp")
public class SpringAsyncConfig  implements AsyncConfigurer {

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new AsyncExceptionHandler();
	}
}
