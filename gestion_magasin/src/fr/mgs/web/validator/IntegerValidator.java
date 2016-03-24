package fr.mgs.web.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Method used to validate an Integer
 * 
 * @author Ismaël
 *
 */
@FacesValidator("intValidator")
public class IntegerValidator implements Validator {

	@Override
	public void validate(FacesContext ctx, UIComponent component, Object value) throws ValidatorException {
		if (!(value instanceof Integer)) {
			throw new ValidatorException(new FacesMessage("La valeur doit être un nombre entier"));
		}
	}
}
