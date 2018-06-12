package tech.adrianohrl.stile.control.bean.personnel.services;

import tech.adrianohrl.stile.control.dao.personnel.SupervisorDAO;
import tech.adrianohrl.stile.model.personnel.Supervisor;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
@ManagedBean
@SessionScoped
public class SupervisorService extends PersonnelService<Supervisor> {

    @Override
    public String getErrorMessage() {
        return "Nenhum supervisor foi cadastrado ainda!!!";
    }

    @Override
    protected List<Supervisor> getElements(EntityManager em) {
        SupervisorDAO supervisorDAO = new SupervisorDAO(em);
        return supervisorDAO.findAll();
    }
    
    public List<Supervisor> getSupervisors() {
        return getElements();
    }
    
}
