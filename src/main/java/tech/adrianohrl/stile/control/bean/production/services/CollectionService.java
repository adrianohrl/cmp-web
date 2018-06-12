package tech.adrianohrl.stile.control.bean.production.services;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import tech.adrianohrl.stile.control.bean.Service;
import tech.adrianohrl.stile.control.dao.production.CollectionDAO;
import tech.adrianohrl.stile.model.production.Collection;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
@ManagedBean
@SessionScoped
public class CollectionService extends Service<Collection> {

    @Override
    public String getErrorMessage() {
        return "Nenhuma coleção foi cadastrada ainda!!!";
    }

    @Override
    protected List<Collection> getElements(EntityManager em) {
        CollectionDAO collectionDAO = new CollectionDAO(em);
        return collectionDAO.findAll();
    }
    
    public List<Collection> getCollections() {
        return getElements();
    }
    
}
