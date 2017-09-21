/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.personnel.validators;

import br.com.ceciliaprado.cmp.control.bean.SessionUtils;
import br.com.ceciliaprado.cmp.model.personnel.Loggable;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author adrianohrl
 * @param <L>
 */
public abstract class LoggableEmployeePasswordValidator<L extends Loggable> extends LoggableEmployeeValidator<L> {

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object value) throws ValidatorException {       
        if(value == null) {
            return;
        }
        String login = SessionUtils.getUserLogin();
        if (login == null) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Erro no login", "O login do funcionário não foi fornecido!!!"));
        }
        for (L loggableEmployee : getLoggableEmployees(fc)) {
            if (login.equals(loggableEmployee.getLogin())) {
                if (!value.equals(loggableEmployee.getPassword())) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Erro no login", "Senha incorreta!!!"));
                }
                return;
            }
        }
        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Erro no login", "Login de funcionário inexistente!!!"));
    }
    
}
