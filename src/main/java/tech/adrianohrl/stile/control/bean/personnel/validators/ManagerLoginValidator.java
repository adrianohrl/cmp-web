/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.bean.personnel.validators;

import tech.adrianohrl.stile.control.bean.personnel.services.ManagerService;
import tech.adrianohrl.stile.model.personnel.Manager;
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
