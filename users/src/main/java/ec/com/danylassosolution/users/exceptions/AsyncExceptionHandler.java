package ec.com.danylassosolution.users.exceptions;

import java.lang.reflect.Method;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
	
	@Override
	public void handleUncaughtException(Throwable ex, Method method, Object... params) {
		String logInformation = String.format(
				"***** FALLO METODO ASYNCRONO *******\nExcepcion:  %s\nClase:  %s\nMetodo:  %s\nParamteros enviados:\n",
				ex.getMessage(), method.getDeclaringClass().getName(), method.getName());
	
		for (final Object param : params) {
			logInformation = logInformation.concat("Param: " + param);
		}
		
		log.error(logInformation);
	}

}
