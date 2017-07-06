/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.personnel.validators;

import br.com.ceciliaprado.cmp.model.personnel.Loggable;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author adrianohrl
 * @param <L>
 */
@FacesValidator("loggableEmployeeLoginValidator")
public class LoggableEmployeeLoginValidator<L extends Loggable> extends LoggableEmployeeValidator<L> {

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object value) throws ValidatorException {
        if(value == null) {
            return;
        }
        for (Loggable loggableEmployee : loggableEmployees) {
            if (loggableEmployee.getLogin().equals(value)) {
                return;
            }
        }
        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro no login", 
                value + " não é login de um dos " + getGroupName() + " cadastrados!!"));
    }
    
}
