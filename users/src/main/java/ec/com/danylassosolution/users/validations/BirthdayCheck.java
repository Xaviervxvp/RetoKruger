package ec.com.danylassosolution.users.validations;

import java.time.LocalDate;
import java.time.Period;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import ec.com.danylassosolution.users.annotations.BirthdayValidationFullAge;

/**
 * Validacion de mayoria de edad
 * @author Xaviervp
 *
 */
public class BirthdayCheck  implements ConstraintValidator<BirthdayValidationFullAge, LocalDate>{

	@Override
	public boolean isValid(LocalDate birthday, ConstraintValidatorContext context) {
		
		Period period = Period.between(birthday, LocalDate.now());
		
		return period.getYears() > 18;
	}

}
