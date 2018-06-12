package tech.adrianohrl.stile.control.bean.production.services;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import tech.adrianohrl.stile.control.bean.Service;
import tech.adrianohrl.stile.control.dao.production.FabricDAO;
import tech.adrianohrl.stile.model.production.Fabric;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
@ManagedBean
@SessionScoped
public class FabricService extends Service<Fabric> {

    @Override
    public String getErrorMessage() {
        return "Nenhum tecido foi cadastrado ainda!!!";
    }

    @Override
    protected List<Fabric> getElements(EntityManager em) {
        FabricDAO fabricDAO = new FabricDAO(em);
        return fabricDAO.findAll();
    }
    
    public List<Fabric> getFabrics() {
        return getElements();
    }
    
}
