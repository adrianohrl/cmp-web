package tech.adrianohrl.stile.control.bean.personnel;

import tech.adrianohrl.stile.control.bean.personnel.services.SupervisorService;
import tech.adrianohrl.stile.model.personnel.Supervisor;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
@ManagedBean
@ViewScoped
public class SupervisorAlterBean extends AlterBean<Supervisor> {
    
    @ManagedProperty(value = "#{supervisorService}")
    private SupervisorService service;

    @Override
    protected void update() {
        service.update();
    }

    @Override
    protected List<Supervisor> getLoggables() {
        return service.getSupervisors();
    }

    public void setService(SupervisorService service) {
        this.service = service;
    }

    @Override
    public Iterator<Supervisor> iterator() {
        return service.iterator();
    }
    
}
