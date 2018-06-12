package tech.adrianohrl.stile.control.bean.production.services;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import tech.adrianohrl.stile.control.bean.Service;
import tech.adrianohrl.stile.control.dao.production.ChartDAO;
import tech.adrianohrl.stile.model.production.Chart;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
@ManagedBean
@SessionScoped
public class ChartService extends Service<Chart> {

    @Override
    public String getErrorMessage() {
        return "Nenhuma grade foi cadastrada ainda!!!";
    }

    @Override
    protected List<Chart> getElements(EntityManager em) {
        ChartDAO chartDAO = new ChartDAO(em);
        return chartDAO.findAll();
    }
    
    public List<Chart> getCharts() {
        return getElements();
    }
    
}
