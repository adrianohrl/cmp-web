package tech.adrianohrl.stile.control.bean.personnel.validators;

import tech.adrianohrl.stile.control.bean.personnel.services.ManagerService;
import tech.adrianohrl.stile.model.personnel.Manager;
import java.util.List;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
@FacesValidator("managerPasswordValidator")
public class ManagerPasswordValidator extends LoggableEmployeePasswordValidator<Manager> {

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
