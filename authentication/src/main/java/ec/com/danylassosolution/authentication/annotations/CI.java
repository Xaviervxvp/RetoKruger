package ec.com.danylassosolution.authentication.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ec.com.danylassosolution.authentication.validations.CiCheck;


/**
 * Anotación que valida los campos o metodos que retornen un CI como string
 * @author Xaviervp
 *
 */
@Constraint(validatedBy = CiCheck.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface CI {

	String message() default "CI Invalid";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
