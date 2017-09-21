/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.personnel.validators;

import br.com.ceciliaprado.cmp.control.bean.personnel.services.ManagerService;
import br.com.ceciliaprado.cmp.model.personnel.Manager;
import java.util.List;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;

/**
 *
 * @author adrianohrl
 */
@FacesValidator("managerLoginValidator")
public class ManagerLoginValidator extends LoggableEmployeeLoginValidator<Manager> {

    @Override
    protected List<Manager> getLoggableEmployees(FacesContext fc) {
        Application application = fc.getApplication();
        ManagerService service = application.evaluateExpressionGet(fc, "#{managerService}", ManagerService.class);
        return service.getManagers();
    }

    @Override
    protected String getGroupName() {
        return "gerentes";
    }
    
}
