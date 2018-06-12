package tech.adrianohrl.stile.control.bean.personnel.services;

import tech.adrianohrl.stile.control.dao.personnel.SubordinateDAO;
import tech.adrianohrl.stile.model.personnel.Subordinate;
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
public class SubordinateService extends PersonnelService<Subordinate> {

    @Override
    public String getErrorMessage() {
        return "Nenhum subordinado foi cadastrado ainda!!!";
    }

    @Override
    protected List<Subordinate> getElements(EntityManager em) {
        SubordinateDAO subordinateDAO = new SubordinateDAO(em);
        return subordinateDAO.findAll();
    }
    
    public List<Subordinate> getSubordinates() {
        return getElements();
    }
    
}
