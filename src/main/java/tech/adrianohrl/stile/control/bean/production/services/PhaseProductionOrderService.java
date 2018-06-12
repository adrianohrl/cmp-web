package tech.adrianohrl.stile.control.bean.production.services;

import tech.adrianohrl.stile.control.bean.Service;
import tech.adrianohrl.stile.control.dao.order.PhaseProductionOrderDAO;
import tech.adrianohrl.stile.model.order.PhaseProductionOrder;
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
public class PhaseProductionOrderService extends Service<PhaseProductionOrder> {

    @Override
    public String getErrorMessage() {
        return "Nenhuma ordem de produção de fase foi cadastrada ainda!!!";
    }

    @Override
    protected List<PhaseProductionOrder> getElements(EntityManager em) {
        PhaseProductionOrderDAO phaseProductionOrderDAO = new PhaseProductionOrderDAO(em);
        return phaseProductionOrderDAO.findAll();
    }
    
    public List<PhaseProductionOrder> getPhaseProductionOrders() {
        return getElements();
    }
    
}
