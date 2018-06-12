package tech.adrianohrl.stile.control.bean.personnel.services;

import tech.adrianohrl.stile.control.dao.personnel.ManagerDAO;
import tech.adrianohrl.stile.model.personnel.Manager;
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
public class ManagerService extends PersonnelService<Manager> {

    @Override
    public String getErrorMessage() {
        return "Nenhum gerente foi cadastrado ainda!!!";
    }

    @Override
    protected List<Manager> getElements(EntityManager em) {
        ManagerDAO managerDAO = new ManagerDAO(em);
        return managerDAO.findAll();
    }
    
    public List<Manager> getManagers() {
        return getElements();
    }
    
}
