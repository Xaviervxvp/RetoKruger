package ec.com.danylassosolution.authentication.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ec.com.danylassosolution.authentication.validations.BirthdayCheck;


/**
 * Comprueba si una fecha de cumpleaños, corresponde a 18 años o más en el pasado
 * @author Xaviervp
 *
 */
@Constraint(validatedBy = BirthdayCheck.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface BirthdayValidationFullAge {

	String message() default "He/She is not of legal age";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
