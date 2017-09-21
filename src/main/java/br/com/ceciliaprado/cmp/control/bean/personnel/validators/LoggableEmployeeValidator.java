/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.personnel.validators;

import br.com.ceciliaprado.cmp.model.personnel.Loggable;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;

/**
 *
 * @author adrianohrl
 * @param <L>
 */
public abstract class LoggableEmployeeValidator<L extends Loggable> implements Validator {
    
    protected abstract List<L> getLoggableEmployees(FacesContext fc);
    
    protected String getGroupName() {
        return "supervisores/gerentes";
    }
    
}
