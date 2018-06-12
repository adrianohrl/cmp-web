package tech.adrianohrl.stile.control.bean.production.services;

import tech.adrianohrl.stile.control.bean.Service;
import tech.adrianohrl.stile.control.dao.production.PhaseDAO;
import tech.adrianohrl.stile.model.production.Phase;
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
public class PhaseService extends Service<Phase> {

    @Override
    public String getErrorMessage() {
        return "Nenhuma fase foi cadastrada ainda!!!";
    }

    @Override
    protected List<Phase> getElements(EntityManager em) {
        PhaseDAO phaseDAO = new PhaseDAO(em);
        return phaseDAO.findAll();
    }
    
    public List<Phase> getPhases() {
        return getElements();
    }
    
}
