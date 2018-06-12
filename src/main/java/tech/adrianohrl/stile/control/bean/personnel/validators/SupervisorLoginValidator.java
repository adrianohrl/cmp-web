package tech.adrianohrl.stile.control.bean.personnel.validators;

import tech.adrianohrl.stile.control.bean.personnel.services.SupervisorService;
import tech.adrianohrl.stile.model.personnel.Supervisor;
import java.util.List;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
@FacesValidator("supervisorLoginValidator")
public class SupervisorLoginValidator extends LoggableEmployeeLoginValidator<Supervisor> {

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
