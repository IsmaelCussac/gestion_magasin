package fr.mgs.web.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Method used to validate a Double
 * 
 * @author Ismaël
 *
 */
@FacesValidator("doubleValidator")
public class DoubleValidator implements Validator{

	@Override
	public void validate(FacesContext ctx, UIComponent component, Object value) throws ValidatorException {
		if (!(value instanceof Double)) {
			 throw new ValidatorException(new FacesMessage("La valeur doit être un nombre réel"));
		}
	}
}
