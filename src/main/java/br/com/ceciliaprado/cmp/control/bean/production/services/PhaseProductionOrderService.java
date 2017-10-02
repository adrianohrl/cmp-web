/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.production.services;

import br.com.ceciliaprado.cmp.control.bean.Service;
import br.com.ceciliaprado.cmp.control.dao.production.PhaseProductionOrderDAO;
import br.com.ceciliaprado.cmp.model.production.PhaseProductionOrder;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
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
