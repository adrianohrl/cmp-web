package tech.adrianohrl.stile.control.bean.personnel.services;

import tech.adrianohrl.stile.control.bean.Service;
import tech.adrianohrl.stile.control.dao.personnel.SectorDAO;
import tech.adrianohrl.stile.model.personnel.Sector;
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
public class SectorService extends Service<Sector> {

    @Override
    public String getErrorMessage() {
        return "Nenhum setor foi cadastrado ainda!!!";
    }

    @Override
    protected List<Sector> getElements(EntityManager em) {
        SectorDAO sectorDAO = new SectorDAO(em);
        return sectorDAO.findAll();
    }

    public List<Sector> getSectors() {
        return getElements();
    }
    
}
