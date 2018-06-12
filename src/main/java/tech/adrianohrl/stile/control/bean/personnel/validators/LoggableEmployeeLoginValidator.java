package tech.adrianohrl.stile.control.bean.personnel.validators;

import tech.adrianohrl.stile.model.personnel.Loggable;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 * @param <L>
 */
public abstract class LoggableEmployeeLoginValidator<L extends Loggable> extends LoggableEmployeeValidator<L> {

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object value) throws ValidatorException {
        if(value == null) {
            return;
        }
        for (L loggableEmployee : getLoggableEmployees(fc)) {
            if (loggableEmployee.getLogin().equals(value)) {
                return;
            }
        }
        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro no login", 
                value + " não é login de um dos " + getGroupName() + " cadastrados!!"));
    }
    
}
