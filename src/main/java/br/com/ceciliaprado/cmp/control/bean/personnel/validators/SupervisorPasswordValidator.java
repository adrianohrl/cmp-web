/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.personnel.validators;

import br.com.ceciliaprado.cmp.control.bean.personnel.services.SupervisorService;
import br.com.ceciliaprado.cmp.model.personnel.Supervisor;
import java.util.List;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;

/**
 *
 * @author adrianohrl
 */
@FacesValidator("supervisorPasswordValidator")
public class SupervisorPasswordValidator extends LoggableEmployeePasswordValidator<Supervisor> {

    @Override
    protected List<Supervisor> getLoggableEmployees(FacesContext fc) {
        Application application = fc.getApplication();
        SupervisorService service = application.evaluateExpressionGet(fc, "#{supervisorService}", SupervisorService.class);
        return service.getSupervisors();
    }

    @Override
    protected String getGroupName() {
        return "supervisores";
    }
    
}
