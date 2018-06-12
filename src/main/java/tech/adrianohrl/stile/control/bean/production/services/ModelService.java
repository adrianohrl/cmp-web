package tech.adrianohrl.stile.control.bean.production.services;

import tech.adrianohrl.stile.control.bean.Service;
import tech.adrianohrl.stile.control.dao.production.ModelDAO;
import tech.adrianohrl.stile.model.production.Model;
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
public class ModelService extends Service<Model> {

    @Override
    public String getErrorMessage() {
        return "Nenhum modelo foi cadastrado ainda!!!";
    }

    @Override
    protected List<Model> getElements(EntityManager em) {
        ModelDAO modelDAO = new ModelDAO(em);
        return modelDAO.findAll();
    }
    
    public List<Model> getModels() {
        return getElements();
    }
    
}
