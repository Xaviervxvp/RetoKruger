package ec.com.danylassosolution.users.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import ec.com.danylassosolution.users.annotations.CI;

/**
 * Validacion aplicada sobre los campos marcados con la anotacion @CI
 * 
 * @author Xaviervp
 *
 */
public class CiCheck implements ConstraintValidator<CI, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		Boolean isCorrect = false;
		try {

			if (value.length() != 10) {
				return false;
			}

			int tercerDigito = Integer.parseInt(value.substring(2, 3));

			if (tercerDigito >= 6) {
				return false;
			}
			
			// Coeficientes de validación cédula
			// El decimo digito se lo considera dígito verificador
			int[] coefValvalue = { 2, 1, 2, 1, 2, 1, 2, 1, 2 };
			int verificador = Integer.parseInt(value.substring(9, 10));
			int suma = 0;
			int digito = 0;
			
			for (int i = 0; i < (value.length() - 1); i++) {
				digito = Integer.parseInt(value.substring(i, i + 1)) * coefValvalue[i];
				suma += ((digito % 10) + (digito / 10));
			}

			if ((suma % 10 == 0) && (suma % 10 == verificador)) {
				isCorrect = true;
			} else if ((10 - (suma % 10)) == verificador) {
				isCorrect = true;
			} else {
				isCorrect = false;
			}

		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
			isCorrect = false;
		} catch (Exception err) {
			err.printStackTrace();
			isCorrect = false;
		}

		return isCorrect;
	}

}
